package jolyjdia.bot.module;

public interface Module {
    default void onLoad() {}
    default void onUnload() {}
}
