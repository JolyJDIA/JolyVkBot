package jolyjdia.bot.scheduler;

import org.jetbrains.annotations.NotNull;

public class Task implements Runnable, Comparable<Task> {
    static final int NO_REPEATING = 0;
    private final Runnable runnable;
    private final int uid;
    private long nextRun;

    private final long period;
    private final boolean asyncMode;
    int heapIndex;

    public Task(boolean asyncMode, Runnable runnable, long period, int id) {
        this.asyncMode = asyncMode;
        this.runnable = runnable;
        this.uid = id;
        this.period = period;
    }
    private Task(Runnable runnable, int id) {
        this(true, runnable, NO_REPEATING, id);
    }

    @Override
    public final void run() {
        if (this.runnable != null) {
            this.runnable.run();
        }
    }

    public boolean isAsync() {
        return asyncMode;
    }

    public final Runnable getRunnable() {
        return runnable;
    }

    public final long getPeriod() {
        return period;
    }

    public final void setNextRun(long nextRun) {
        this.nextRun = nextRun;
    }

    public final long getNextRun() {
        return nextRun;
    }

    public final int getUid() {
        return uid;
    }
    public boolean isPeriodic() {
        return period != NO_REPEATING;
    }

    public int getHeapIndex() {
        return heapIndex;
    }

    public void setHeapIndex(int heapIndex) {
        this.heapIndex = heapIndex;
    }

    @Override
    public final int hashCode() {
        return uid;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        //не юзал компаратор, ибо он вернет 0, когда o == this
        return uid == task.uid && nextRun == task.nextRun;
    }

    @Override
    public final int compareTo(@NotNull Task o) {
        if (o == this) {
            return 0;
        }
        //не может быть у объедков одинаковый айди, поэтому без 0
        return (nextRun < o.nextRun) ? -1 : ((nextRun == o.nextRun) ? (uid < o.uid ? -1 : 1) : 1);
    }
}