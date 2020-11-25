package jolyjdia.bot.events.user;

import jolyjdia.bot.events.Cancellable;
import jolyjdia.bot.events.Event;
import jolyjdia.bot.objects.User;

public class BotSendCommandEvent implements Event, Cancellable {
    private final String[] args;
    private final User user;

    public BotSendCommandEvent(User user, String[] args) {
        this.user = user;
        this.args = args;
    }
    public final User getUser() {
        return user;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }
}
