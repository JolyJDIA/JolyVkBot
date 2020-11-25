package jolyjdia.vk.sdk.callback;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import jolyjdia.bot.utils.StringBind;
import jolyjdia.vk.sdk.objects.messages.Message;
import jolyjdia.vk.sdk.objects.messages.NewMessage;

import java.lang.reflect.Type;
import java.util.Map;

@SuppressWarnings("unused")
public class CallbackApi {
    private static final String CALLBACK_EVENT_MESSAGE_NEW = "message_new";
    private static final String CALLBACK_EVENT_MESSAGE_REPLY = "message_reply";
    private static final String CALLBACK_EVENT_MESSAGE_ALLOW = "message_allow";
    private static final String CALLBACK_EVENT_MESSAGE_DENY = "message_deny";
    private static final String CALLBACK_EVENT_MESSAGE_EDIT = "message_edit";
    private static final String CALLBACK_EVENT_PHOTO_NEW = "photo_new";
    private static final String CALLBACK_EVENT_PHOTO_COMMENT_NEW = "photo_comment_new";
    private static final String CALLBACK_EVENT_PHOTO_COMMENT_EDIT = "photo_comment_edit";
    private static final String CALLBACK_EVENT_PHOTO_COMMENT_RESTORE = "photo_comment_restore";
    private static final String CALLBACK_EVENT_PHOTO_COMMENT_DELETE = "photo_comment_delete";
    private static final String CALLBACK_EVENT_AUDIO_NEW = "audio_new";
    private static final String CALLBACK_EVENT_VIDEO_NEW = "video_new";
    private static final String CALLBACK_EVENT_VIDEO_COMMENT_NEW = "video_comment_new";
    private static final String CALLBACK_EVENT_VIDEO_COMMENT_EDIT = "video_comment_edit";
    private static final String CALLBACK_EVENT_VIDEO_COMMENT_RESTORE = "video_comment_restore";
    private static final String CALLBACK_EVENT_VIDEO_COMMENT_DELETE = "video_comment_delete";
    private static final String CALLBACK_EVENT_WALL_POST_NEW = "wall_post_new";
    private static final String CALLBACK_EVENT_WALL_REPOST = "wall_repost";
    private static final String CALLBACK_EVENT_WALL_REPLY_NEW = "wall_reply_new";
    private static final String CALLBACK_EVENT_WALL_REPLY_EDIT = "wall_reply_edit";
    private static final String CALLBACK_EVENT_WALL_REPLY_RESTORE = "wall_reply_restore";
    private static final String CALLBACK_EVENT_WALL_REPLY_DELETE = "wall_reply_delete";
    private static final String CALLBACK_EVENT_BOARD_POST_NEW = "board_post_new";
    private static final String CALLBACK_EVENT_BOARD_POST_EDIT = "board_post_edit";
    private static final String CALLBACK_EVENT_BOARD_POST_RESTORE = "board_post_restore";
    private static final String CALLBACK_EVENT_BOARD_POST_DELETE = "board_post_delete";
    private static final String CALLBACK_EVENT_MARKET_COMMENT_NEW = "market_comment_new";
    private static final String CALLBACK_EVENT_MARKET_COMMENT_EDIT = "market_comment_edit";
    private static final String CALLBACK_EVENT_MARKET_COMMENT_RESTORE = "market_comment_restore";
    private static final String CALLBACK_EVENT_MARKET_COMMENT_DELETE = "market_comment_delete";
    private static final String CALLBACK_EVENT_GROUP_LEAVE = "group_leave";
    private static final String CALLBACK_EVENT_GROUP_JOIN = "group_join";
    private static final String CALLBACK_EVENT_GROUP_CHANGE_SETTINGS = "group_change_settings";
    private static final String CALLBACK_EVENT_GROUP_CHANGE_PHOTO = "group_change_photo";
    private static final String CALLBACK_EVENT_GROUP_OFFICERS_EDIT = "group_officers_edit";
    private static final String CALLBACK_EVENT_POLL_VOTE_NEW = "poll_vote_new";
    private static final String CALLBACK_EVENT_USER_BLOCK = "user_block";
    private static final String CALLBACK_EVENT_USER_UNBLOCK = "user_unblock";
    private static final String CALLBACK_EVENT_CONFIRMATION = "confirmation";
    private static final Map<String, Type> CALLBACK_TYPES;

    static {

        CALLBACK_TYPES = Map.ofEntries(
                Map.entry(CALLBACK_EVENT_MESSAGE_NEW, new TypeToken<CallbackMessage<NewMessage>>(){}.getType()),
                Map.entry(CALLBACK_EVENT_MESSAGE_REPLY, new TypeToken<CallbackMessage<Message>>(){}.getType()),
                Map.entry(CALLBACK_EVENT_MESSAGE_EDIT, new TypeToken<CallbackMessage<Message>>(){}.getType()));
    }

    public void messageNew(Integer groupId, Message message) {
    }

    public void messageNew(Integer groupId, String secret, Message message) {
        messageNew(groupId, message);
    }

    public void messageReply(Integer groupId, Message message) {
    }

    public void messageReply(Integer groupId, String secret, Message message) {
        messageReply(groupId, message);
    }

    public void messageEdit(Integer groupId, Message message) {
    }

    public void messageEdit(Integer groupId, String secret, Message message) {
        messageEdit(groupId, message);
    }

    public void confirmation(Integer groupId) {
    }

    public void confirmation(Integer groupId, String secret) {
        confirmation(groupId);
    }

    public boolean parse(String json) {
        JsonObject jsonObject = StringBind.fromJson(json, JsonObject.class);
        return parse(jsonObject);
    }

    public boolean parse(JsonObject json) {
        String type = json.get("type").getAsString();
        if (type.equalsIgnoreCase(CALLBACK_EVENT_CONFIRMATION)) {
            ConfirmationMessage message = StringBind.fromJson(json, ConfirmationMessage.class);
            confirmation(message.getGroupId(), message.getSecret());
            return true;
        }

        Type typeOfClass = CALLBACK_TYPES.get(type);
        if (typeOfClass == null) {
            return false;
        }

        @SuppressWarnings("rawtypes")
        CallbackMessage message = StringBind.fromJson(json, typeOfClass);
        switch (type) {
            case CALLBACK_EVENT_MESSAGE_NEW -> {
                messageNew(message.getGroupId(), message.getSecret(), ((NewMessage) message.getObject()).getMessage());
            }
            case CALLBACK_EVENT_MESSAGE_REPLY -> messageReply(message.getGroupId(), message.getSecret(), (Message) message.getObject());
            case CALLBACK_EVENT_MESSAGE_EDIT -> messageEdit(message.getGroupId(), message.getSecret(), (Message) message.getObject());
            default -> {
                return false;
            }
        }

        return true;
    }
}
