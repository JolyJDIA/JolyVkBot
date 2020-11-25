package jolyjdia.vk.sdk.queries.messages;

import jolyjdia.vk.sdk.actors.GroupActor;
import jolyjdia.vk.sdk.actors.UserActor;
import jolyjdia.vk.sdk.client.AbstractQueryBuilder;
import jolyjdia.vk.sdk.client.VkApiClient;

import java.util.List;

public final class MessagesSendQuery extends AbstractQueryBuilder<Integer> {
    public MessagesSendQuery(VkApiClient client, UserActor actor) {
        super(client, "messages.send", actor);
    }
    
    public MessagesSendQuery(VkApiClient client, GroupActor actor) {
        super(client, "messages.send", actor);
        groupId(actor.getGroupId());
    }
    
    public MessagesSendQuery userId(int value) {
        unsafeParam("user_id", value);
        return this;
    }
    
    public MessagesSendQuery randomId(int value) {
        unsafeParam("random_id", value);
        return this;
    }
    
    public MessagesSendQuery peerId(Integer value) {
        unsafeParam("peer_id", value);
        return this;
    }
    
    public MessagesSendQuery domain(String value) {
        unsafeParam("domain", value);
        return this;
    }
    public MessagesSendQuery chatId(int value) {
        unsafeParam("chat_id", value);
        return this;
    }
    
    public MessagesSendQuery message(String value) {
        unsafeParam("message", value);
        return this;
    }
    
    public MessagesSendQuery lat(Number value) {
        unsafeParam("lat", value);
        return this;
    }

    public MessagesSendQuery lng(Number value) {
        unsafeParam("long", value);
        return this;
    }

    public MessagesSendQuery attachment(String value) {
        unsafeParam("attachment", value);
        return this;
    }

    public MessagesSendQuery replyTo(Integer value) {
        unsafeParam("reply_to", value);
        return this;
    }

    public MessagesSendQuery forward(String value) {
        unsafeParam("forward", value);
        return this;
    }

    public MessagesSendQuery stickerId(int value) {
        unsafeParam("sticker_id", value);
        return this;
    }

    public MessagesSendQuery groupId(int value) {
        unsafeParam("group_id", value);
        return this;
    }

/*
    public MessagesSendQuery keyboard(Keyboard value) {
        unsafeParam("keyboard", value);
        return this;
    }
*/

    public MessagesSendQuery payload(String value) {
        unsafeParam("payload", value);
        return this;
    }

    public MessagesSendQuery dontParseLinks(boolean value) {
        unsafeParam("dont_parse_links", value);
        return this;
    }

    public MessagesSendQuery disableMentions(boolean value) {
        unsafeParam("disable_mentions", value);
        return this;
    }

    public MessagesSendQuery userIds(int... value) {
        unsafeParam("user_ids", value);
        return this;
    }

    public MessagesSendQuery userIds(List<Integer> value) {
        unsafeParam("user_ids", value);
        return this;
    }

    public MessagesSendQuery forwardMessages(int... value) {
        unsafeParam("forward_messages", value);
        return this;
    }

    public MessagesSendQuery forwardMessages(List<Integer> value) {
        unsafeParam("forward_messages", value);
        return this;
    }
}