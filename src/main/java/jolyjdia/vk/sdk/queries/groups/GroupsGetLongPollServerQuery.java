package jolyjdia.vk.sdk.queries.groups;

import jolyjdia.vk.sdk.actors.GroupActor;
import jolyjdia.vk.sdk.actors.UserActor;
import jolyjdia.vk.sdk.client.AbstractQueryBuilder;
import jolyjdia.vk.sdk.client.VkApiClient;
import jolyjdia.vk.sdk.objects.groups.LongPollServer;

public final class GroupsGetLongPollServerQuery extends AbstractQueryBuilder<LongPollServer> {
    public GroupsGetLongPollServerQuery(VkApiClient client, UserActor actor, int groupId) {
        super(client, "groups.getLongPollServer", actor);
        groupId(groupId);
    }

    public GroupsGetLongPollServerQuery(VkApiClient client, GroupActor actor) {
        super(client, "groups.getLongPollServer", actor);
        groupId(actor.getGroupId());
    }

    private void groupId(int value) {
        unsafeParam("group_id", value);
    }
}
