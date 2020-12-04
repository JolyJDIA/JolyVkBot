package jolyjdia.bot.scheduler;

import jolyjdia.bot.Bot;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static jolyjdia.bot.Bot.ASYNC_POOL;

public class BotScheduler {
    private static final AtomicInteger ids = new AtomicInteger();
    private final RoflanBlockingQueue taskQueue = new RoflanBlockingQueue();
    private static final int SAMPLE_INTERVAL = 100;
    public final double[] recentTps = {20, 20, 20};
    long tickSection = System.currentTimeMillis();

    public void mainThreadHeartbeat() {
        long now = System.currentTimeMillis();
        if (now % SAMPLE_INTERVAL == 0) {
            double currentTps = 1E3 / (now - tickSection) * SAMPLE_INTERVAL;
            recentTps[0] = calcTps(recentTps[0], 0.92, currentTps); // 1/exp(5sec/1min)
            recentTps[1] = calcTps(recentTps[1], 0.9835, currentTps); // 1/exp(5sec/5min)
            recentTps[2] = calcTps(recentTps[2], 0.9945, currentTps); // 1/exp(5sec/15min)
            tickSection = now;
        }
        Task task;
        if ((task = taskQueue.peek()) == null) {
            return;
        }
        if (now >= task.nextRun) {
            if (task.isAsync()) {
                ASYNC_POOL.execute(task);
            } else {
                task.run();
            }
            if (!task.isPeriodic()) {
                taskQueue.finishPoll();
                System.out.println((task.isAsync() ? "Async" : "Sync") + "Scheduler: task deleted (" + task.getUid() + ')');
                return;
            }
            taskQueue.setNexRun(now + task.period);
        }
    }

    public <T> Future<T> submitAsync(Callable<T> callable) {
        return ASYNC_POOL.submit(callable);
    }

    public final void runSyncTask(Runnable runnable) {
        if (Thread.currentThread() == Bot.getLeader()) {
            runnable.run();
        } else {
            addTask(false, runnable, Task.NO_REPEATING, Task.NO_REPEATING);
        }
    }

    public final void runAsyncTask(Runnable runnable) {
        ASYNC_POOL.execute(runnable);
    }

    public final Task runRepeatingSyncTaskAfter(Runnable runnable, int delay, int period, TimeUnit unit) {
        return addTask(false, runnable, delay, period, unit);
    }

    public final Task runSyncTaskAfter(Runnable runnable, int delay, TimeUnit unit) {
        return addTask(false, runnable, delay, Task.NO_REPEATING, unit);
    }

    public final Task runAsyncTaskAfter(Runnable runnable, int delay, TimeUnit unit) {
        return addTask(true, runnable, delay, Task.NO_REPEATING, unit);
    }

    public final Task runRepeatingAsyncTaskAfter(Runnable runnable, int delay, int period, TimeUnit unit) {
        return addTask(true, runnable, delay, period, unit);
    }

    public final void cancelTask(Task task) {
        taskQueue.remove(task);
    }

    public final void cancelTasks() {
        taskQueue.clear();
    }

    private Task addTask(boolean async, Runnable command, long delay, long period, TimeUnit unit) {
        return addTask(async, command, unit.toMillis(delay), unit.toMillis(period));
    }
    private Task addTask(boolean async, Runnable command, long delay, long period) {
        if (command == null) {
            throw new NullPointerException();
        }
        Task task = new Task(async, command, period, ids.getAndIncrement());
        task.setNextRun(System.currentTimeMillis() + delay);
        taskQueue.add(task);
        return task;
    }
    private static double calcTps(double avg, double exp, double tps) {
        return (avg * exp) + (tps * (1 - exp));
    }
}