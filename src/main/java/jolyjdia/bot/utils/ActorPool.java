package jolyjdia.bot.utils;

import jolyjdia.vk.sdk.actors.Actor;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ActorPool {
    volatile Node head;
    private final Node tail;

    public ActorPool() {
        head = tail = new Node();
    }
    public ActorPool(Collection<? extends Actor> c) {
        Node h = null, t = null;
        for (Actor e : c) {
            Node newNode = new Node(Objects.requireNonNull(e));
            if (h == null)
                h = t = newNode;
            else
                t.appendRelaxed(t = newNode);
        }
        if (h == null)
            h = t = new Node();
        head = h;
        tail = t;
    }

    static final class Node {
        volatile Actor item;
        volatile Node next;

        /**
         * Constructs a node holding item.  Uses relaxed write because
         * item can only be seen after piggy-backing publication via CAS.
         */
        Node(Actor item) {
            ITEM.set(this, item);
        }

        /** Constructs a dead dummy node. */
        Node() {}

        void appendRelaxed(Node next) {
            // assert next != null;
            // assert this.next == null;
            NEXT.set(this, next);
        }

        boolean remove(Actor cmp) {
            // assert item == cmp || item == null;
            // assert cmp != null;
            // assert val == null;
            return ITEM.compareAndSet(this, cmp, (Actor) null);
        }
    }
    public void addWorker(Actor e) {
        final Node newNode = new Node(Objects.requireNonNull(e));

        for (Node t = tail, p = t;;) {
            Node q = p.next;
            if (q == null) {
                // p is last node
                if (NEXT.compareAndSet(p, null, newNode)) {
                    // Successful CAS is the linearization point
                    // for e to become an element of this queue,
                    // and for newNode to become "live".
                    if (p != t) // hop two nodes at a time; failure is OK
                        TAIL.weakCompareAndSet(this, t, newNode);
                    return;
                }
                // Lost CAS race to another thread; re-read next
            }
            else if (p == q)
                // We have fallen off list.  If tail is unchanged, it
                // will also be off-list, in which case we need to
                // jump to head, from which all live nodes are always
                // reachable.  Else the new tail is a better bet.
                p = (t != (t = tail)) ? t : head;
            else
                // Check for tail updates after two hops.
                p = (p != t && t != (t = tail)) ? t : q;
        }
    }

    public void execute(Consumer<Actor> consumer) {
        restartFromHead: for (;;) {
            for (Node h = head, p = h, q;; p = q) {
                final Actor item;
                if ((item = p.item) != null && p.remove(item)) {
                    // Successful CAS is the linearization point
                    // for item to be removed from this queue.
                    if (p != h) // hop two nodes at a time
                        updateHead(h, ((q = p.next) != null) ? q : p);
                    consumer.accept(item);
                    addWorker(item);
                    return;
                }
                else if ((q = p.next) == null) {
                    updateHead(h, p);
                    return;
                }
                else if (p == q)
                    continue restartFromHead;
            }
        }
    }
    private static final int MAX_HOPS = 8;
    public void execute(Predicate<Actor> filter, Consumer<Actor> consumer) {
        restartFromHead: for (;;) {
            int hops = MAX_HOPS;
            // c will be CASed to collapse intervening dead nodes between
            // pred (or head if null) and p.
            for (Node p = head, c = p, pred = null, q; p != null; p = q) {
                q = p.next;
                final Actor item; boolean pAlive;
                if (pAlive = ((item = p.item) != null)) {
                    if (filter.test(item)) {
                        if (p.remove(item)) {
                            consumer.accept(item);
                            addWorker(item);
                        }
                        pAlive = false;
                    }
                }
                if (pAlive || q == null || --hops == 0) {
                    // p might already be self-linked here, but if so:
                    // - CASing head will surely fail
                    // - CASing pred's next will be useless but harmless.
                    if ((c != p && !tryCasSuccessor(pred, c, c = p))
                            || pAlive) {
                        // if CAS failed or alive, abandon old pred
                        hops = MAX_HOPS;
                        pred = p;
                        c = q;
                    }
                } else if (p == q)
                    continue restartFromHead;
            }
            return;
        }
    }
    final void updateHead(Node h, Node p) {
        // assert h != null && p != null && (h == p || h.item == null);
        if (h != p && HEAD.compareAndSet(this, h, p))
            NEXT.setRelease(h, h);
    }
    private boolean tryCasSuccessor(Node pred, Node c, Node p) {
        // assert p != null;
        // assert c.item == null;
        // assert c != p;
        if (pred != null)
            return NEXT.compareAndSet(pred, c, p);
        if (HEAD.compareAndSet(this, c, p)) {
            NEXT.setRelease(c, c);
            return true;
        }
        return false;
    }
    // VarHandle mechanics
    private static final VarHandle HEAD;
    private static final VarHandle TAIL;
    static final VarHandle ITEM;
    static final VarHandle NEXT;
    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            HEAD = l.findVarHandle(ActorPool.class, "head", Node.class);
            TAIL = l.findVarHandle(ActorPool.class, "tail", Node.class);
            ITEM = l.findVarHandle(Node.class, "item", Actor.class);
            NEXT = l.findVarHandle(Node.class, "next", Node.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
