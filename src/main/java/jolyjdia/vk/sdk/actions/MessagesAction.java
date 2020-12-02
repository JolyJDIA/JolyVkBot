package jolyjdia.vk.sdk.actions;

import jolyjdia.vk.sdk.actors.GroupActor;
import jolyjdia.vk.sdk.actors.UserActor;
import jolyjdia.vk.sdk.client.VkApiClient;
import jolyjdia.vk.sdk.queries.messages.MessagesSendQuery;

public class MessagesAction extends AbstractAction {

    public MessagesAction(VkApiClient client) {
        super(client);
    }
    public MessagesSendQuery send(UserActor actor) {
        return new MessagesSendQuery(client, actor);
    }

    public MessagesSendQuery send(GroupActor actor) {
        return new MessagesSendQuery(client, actor);
    }
}