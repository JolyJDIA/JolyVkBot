package jolyjdia.bot.events;

public interface Cancellable {
    boolean isCancelled();
    void setCancelled(boolean cancel);
}