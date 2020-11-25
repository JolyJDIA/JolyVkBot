package jolyjdia.bot.utils;

import jolyjdia.bot.Bot;
import jolyjdia.vk.sdk.queries.messages.MessagesSendQuery;
import org.jetbrains.annotations.NonNls;

@NonNls
public final class MessageChannel {
    public static final int CHAT_ID = 2000000000;
    public static final int BOUND = 10000;

    private MessageChannel() {}

    public static void sendMessage(String msg, int peerId) {
        if (msg.isEmpty()) {
            return;
        }
        //msg = '[' +Thread.currentThread().getName()+"]:\n" + msg;
        builder(peerId).message(msg).execute();
    }
    public static void sendAttachments(String attachments, int peerId) {
        if (attachments.isEmpty()) {
            return;
        }
        builder(peerId).attachment(attachments).execute();
    }
    public static void sendMessage(String msg, int peerId, String attachments) {
        msg = '[' +Thread.currentThread().getName()+"]:\n" + msg;
        builder(peerId).message(msg).attachment(attachments).execute();
    }

    /*public static void sendKeyboard(String msg, int peerId, Keyboard keyboard) {
        msg = '[' +Thread.currentThread().getName()+"]:\n" + msg;
        builder(peerId).keyboard(keyboard).message(msg).execute();
    }

    public static void editChat(String title, int peerId) {
        Bot.getVkApiClient().messages().editChat(Bot.getGroupActor(), chatId(peerId), title).execute();
    }*/

    public static MessagesSendQuery builder(int peerId) {
        return Bot.getVkApiClient().messages()
                .send(Bot.getGroupActor())
                .randomId(MathUtils.randomInt(BOUND))
                .peerId(peerId);
    }
    public static int chatId(int peerId) {
        return peerId-CHAT_ID;
    }
}
