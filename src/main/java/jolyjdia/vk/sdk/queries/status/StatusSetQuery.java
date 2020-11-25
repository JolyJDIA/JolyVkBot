package jolyjdia.vk.sdk.queries.status;

import jolyjdia.vk.sdk.actors.UserActor;
import jolyjdia.vk.sdk.client.AbstractQueryBuilder;
import jolyjdia.vk.sdk.client.VkApiClient;

public class StatusSetQuery extends AbstractQueryBuilder<Integer> {
    public StatusSetQuery(VkApiClient client, UserActor actor) {
        super(client, "status.set", actor);
    }
    public StatusSetQuery text(String value) {
        unsafeParam("text", value);
        return this;
    }
    public StatusSetQuery groupId(int value) {
        unsafeParam("group_id", value);
        return this;
    }
}
