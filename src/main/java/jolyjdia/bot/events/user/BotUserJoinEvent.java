package jolyjdia.bot.events.user;

import jolyjdia.bot.objects.User;

public class BotUserJoinEvent extends UserEvent {
    public BotUserJoinEvent(User user) {
        super(user);
    }
}
