package jolyjdia.vk.sdk.actions;

import jolyjdia.vk.sdk.actors.GroupActor;
import jolyjdia.vk.sdk.actors.UserActor;
import jolyjdia.vk.sdk.client.VkApiClient;
import jolyjdia.vk.sdk.queries.groups.GroupsGetLongPollServerQuery;

public class GroupsLongPollAction extends AbstractAction {
    public GroupsLongPollAction(VkApiClient client) {
        super(client);
    }

    public GroupsGetLongPollServerQuery getLongPollServer(UserActor actor, int groupId) {
        return new GroupsGetLongPollServerQuery(client, actor, groupId);
    }

    public GroupsGetLongPollServerQuery getLongPollServer(GroupActor actor, int groupId) {
        return new GroupsGetLongPollServerQuery(client, actor);
    }

}
