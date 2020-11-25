package jolyjdia.bot.utils.cache;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ConcurrentCache<K, V> {
    static final int NESTED = -1;
    private final ConcurrentHashMap<K, Node<V>> map;
    private final CacheBuilder<K, V> builder;
    private final CacheBuilder.AsyncCacheLoader<K, V> cacheLoader;

    public ConcurrentCache(CacheBuilder.AsyncCacheLoader<K, V> cacheLoader, CacheBuilder<K, V> builder) {
        this.cacheLoader = cacheLoader;
        float loadFactor = builder.getLoadFactor();
        int buckets = (int)(builder.getMaxSize() / loadFactor) + 1;
        this.map = new ConcurrentHashMap<>(buckets, loadFactor, builder.getConcurrencyLevel());
        this.builder = builder;
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(builder.getTick());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                for (Map.Entry<K, Node<V>> entry : map.entrySet()) {
                    Node<V> vNode = entry.getValue();
                    if (vNode.isRemoving()) {//Процесс удаления уже идет
                        return;
                    }
                    long afterAccess = builder.getExpireAfterAccess(),
                            afterWrite = builder.getExpireAfterWrite(),
                            now = System.currentTimeMillis();
                    if ((afterAccess != NESTED && now - vNode.refresh >= afterAccess) ||
                        (afterWrite  != NESTED && now - vNode.start   >= afterWrite)
                    ) {
                        vNode.addRemovalStatus(safeRemoval(entry.getKey(), vNode).thenApply(remove -> {
                            if (remove) {
                                if (vNode.isRemoving()) {//Проверяю, вдруг я уже где-то обновил значение
                                    map.remove(entry.getKey());
                                }
                            } else {
                                vNode.refresh = System.currentTimeMillis();
                            }
                            vNode.clearRemovalStatus();//Закончил удалять
                            return remove;
                        }));
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.setName("cleaner");
        thread.start();
    }

    public CompletableFuture<V> getAndPut(K key) {
        return map.compute(key, (k, oldValue) -> {
            if (oldValue == null) {
                return new Node<>(cacheLoader.asyncLoad(key, builder.getExecutor()));
            } else {
                oldValue.refresh = System.currentTimeMillis();
                oldValue.interruptRemoval();
                //чек добавлять
                return oldValue;
            }
        }).cf;
    }
    public CompletableFuture<Boolean> remove(K key) {
        Node<V> node = map.get(key);
        if (node == null) {
            return CompletableFuture.failedFuture(new NullPointerException());
        }
        CompletableFuture<Boolean> cf;
        if ((cf = node.removal) == null) {
            node.addRemovalStatus((cf = safeRemoval(key, node).thenApply(f -> {
                if (f) {
                    if (node.isRemoving()) {//Проверяю, вдруг я уже где-то обновил значение
                        map.remove(key);
                    }
                } else {
                    node.refresh = System.currentTimeMillis();
                }
                node.clearRemovalStatus();//Закончил удалять
                return f;
            })));
        }
        return cf;
    }
    public List<CompletableFuture<Boolean>> removeAll() {
        final Set<Map.Entry<K, Node<V>>> entrySet = map.entrySet();
        LinkedList<CompletableFuture<Boolean>> cfs = new LinkedList<>();
        for(Map.Entry<K, Node<V>> e : entrySet) {
            Node<V> node = e.getValue(); CompletableFuture<Boolean> removal;
            if ((removal = node.removal) == null) {
                K key = e.getKey();
                node.addRemovalStatus(removal = safeRemoval(key, node).thenApply(f -> {
                    if (f) {
                        if (node.isRemoving()) {//Проверяю, вдруг я уже где-то обновил значение
                            map.remove(key);
                        }
                    } else {
                        node.refresh = System.currentTimeMillis();
                    }
                    node.clearRemovalStatus();//Закончил удалять
                    return f;
                }));
            }
            cfs.add(removal);
        }
        return cfs;
    }

    private static class Node<V> {
        private final @NotNull CompletableFuture<V> cf;
        private final long start = System.currentTimeMillis();
        private volatile long refresh = start;
        //показатель статуса
        private volatile CompletableFuture<Boolean> removal;//volatile

        public Node(@NotNull CompletableFuture<V> cf) {
            this.cf = cf;
        }

        private synchronized void addRemovalStatus(CompletableFuture<Boolean> removal) {
            if (this.removal == null) {
                this.removal = removal;
            }
        }
        private synchronized void clearRemovalStatus() {
            this.removal = null;
        }
        private synchronized void interruptRemoval() {
            if (this.removal == null) {
                return;
            }
            this.removal.cancel(true);
            this.removal = null;
        }
        public boolean isRemoving() {
            return removal != null && !removal.isCancelled();
        }
        public boolean isRemove() {
            try {
                return removal != null && removal.isDone() && removal.get();//get
            } catch (InterruptedException | ExecutionException e) {
                return false;
            }
        }
        @Override
        public String toString() {
            @NonNls String s = "Node{" +
                    "CompletableFuture=" + cf +
                    ", start=" + start +
                    ", refresh=" + refresh;
            if (isRemoving()) {
                s += ", status=removal";
            }
            s += '}';
            return s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return start == node.start && cf.equals(node.cf);
        }

        @Override
        public int hashCode() {
            return cf.hashCode() ^ (int)(start ^ (start >>> 32));
        }
    }
    private CompletableFuture<Boolean> safeRemoval(K key, @NotNull Node<V> node) {
        //Согласное спеки, завершенные Cf выполнятся в этом треде
        CompletableFuture<V> cf = node.cf;

        return cf.thenComposeAsync(f -> {
            return builder.getRemoval().onRemoval(key, cf);
        }, builder.getExecutor());
    }
    public int size() {
        return map.size();
    }
    @Override
    public String toString() {
        return "ConcurrentCache{map=" + map + '}';
    }
}
