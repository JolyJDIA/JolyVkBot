package jolyjdia.vk.sdk.queries.status;

import jolyjdia.vk.sdk.actors.UserActor;
import jolyjdia.vk.sdk.client.AbstractQueryBuilder;
import jolyjdia.vk.sdk.client.VkApiClient;
import jolyjdia.vk.sdk.objects.status.Status;

public class StatusGetQuery extends AbstractQueryBuilder<Status> {
    public StatusGetQuery(VkApiClient client, UserActor actor) {
        super(client, "status.get", actor);
    }
    public StatusGetQuery userId(int value) {
        unsafeParam("user_id", value);
        return this;
    }

    public StatusGetQuery groupId(int value) {
        unsafeParam("group_id", value);
        return this;
    }
}
