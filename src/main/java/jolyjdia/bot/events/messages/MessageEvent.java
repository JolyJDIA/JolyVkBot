package jolyjdia.bot.events.messages;

import jolyjdia.bot.events.Event;
import jolyjdia.bot.objects.User;
import jolyjdia.vk.sdk.objects.messages.Message;

public class MessageEvent implements Event {
    private final Message msg;
    private final User user;

    public MessageEvent(User user, Message msg) {
        this.msg = msg;
        this.user = user;
    }

    public final User getUser() {
        return user;
    }

    public final Message getMessage() {
        return msg;
    }
}