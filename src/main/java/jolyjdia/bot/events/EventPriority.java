package jolyjdia.bot.events;

public enum EventPriority {
    LOWEST(-2),
    LOW(-1),
    NORMAL(0),
    HIGH(1),
    HIGHEST(2);

    private final int slot;

    EventPriority(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}