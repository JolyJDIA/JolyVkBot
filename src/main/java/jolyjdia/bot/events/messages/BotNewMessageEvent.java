package jolyjdia.bot.events.messages;

import jolyjdia.bot.objects.User;
import jolyjdia.vk.sdk.objects.messages.Message;

public class BotNewMessageEvent extends MessageEvent {
    public BotNewMessageEvent(User user, Message msg) {
        super(user, msg);
    }
}
