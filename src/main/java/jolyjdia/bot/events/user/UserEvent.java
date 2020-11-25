package jolyjdia.bot.events.user;

import jolyjdia.bot.events.Event;
import jolyjdia.bot.objects.User;

public class UserEvent implements Event {
    private final User user;

    public UserEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
