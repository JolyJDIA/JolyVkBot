package jolyjdia.bot.events.user;

import jolyjdia.bot.objects.User;

public class BotUserLeaveEvent extends UserEvent {
    public BotUserLeaveEvent(User user) {
        super(user);
    }
}
