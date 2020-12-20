package jolyjdia.bot;

import jolyjdia.bot.events.Event;
import jolyjdia.bot.events.messages.BotNewMessageEvent;
import jolyjdia.bot.events.user.BotSendCommandEvent;
import jolyjdia.bot.objects.User;
import jolyjdia.bot.utils.StringBind;
import jolyjdia.vk.sdk.callback.CallbackApi;
import jolyjdia.vk.sdk.objects.messages.Message;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CallbackApiLongPollHandler extends CallbackApi {
    //  private final CommandExecutor executor = new CommandExecutor(Runtime.getRuntime().availableProcessors());

    @Override
    public final void messageNew(Integer groupId, @NotNull Message msg) {
        if(msg.getFromId() < 0) {
            return;
        }
        User user = Bot.getUserManager().getChatByUser(msg.getFromId(), msg.getPeerId());
        /*MessageAction action = msg.getAction();
        User user = Bot.getUserManager().getChatByUser(msg.getFromId(), msg.getPeerId());
        if(action != null) {
            MessageActionStatus type = action.getType();
            if(type == MessageActionStatus.CHAT_KICK_USER) {
                Bot.getUserManager().deleteUser(msg.getPeerId(), msg.getFromId());
                //Удаляю из бд беседу

                submitEvent(new BotUserLeaveEvent(user));
                return;
            } else if(type == MessageActionStatus.CHAT_INVITE_USER || type == MessageActionStatus.CHAT_INVITE_USER_BY_LINK) {
                submitEvent(new BotUserJoinEvent(user));
                return;
            }
        }*/
        @NonNls String text = msg.getText();
        if(text.length() > 1 && (text.charAt(0) == '/' || text.charAt(0) == '!')) {
            String[] args = text.substring(1).split(" ");
            long start = System.currentTimeMillis();
            if(Bot.getBotManager().getRegisteredCommands().stream().noneMatch(cmd -> {
                if(cmd.equalsCommand(args[0])) {
                    int len = args.length;
                    String[] args0 = new String[len-1];
                    System.arraycopy(args, 1, args0, 0, len - 1);
                    cmd.execute(user, args0);
                    submitEvent(new BotSendCommandEvent(user, args0));
                    return true;
                }
                return false;
            })) { return; }
            @NonNls long end = System.currentTimeMillis() - start;
            StringBind.log("Команда: "+ Arrays.toString(args) +" ("+end+"ms) Чат: " +msg.getPeerId() + '(' +msg.getFromId()+ ')');
            return;
        }
        StringBind.log("Сообщение: \""+ msg.getText()+ "\" Чат: " +msg.getPeerId() + '(' +msg.getFromId()+ ')');
        submitEvent(new BotNewMessageEvent(user, msg));
    }

    private static void submitEvent(Event event) {
        Bot.getBotManager().getListeners().forEach(handler -> handler.accept(event));
    }
}