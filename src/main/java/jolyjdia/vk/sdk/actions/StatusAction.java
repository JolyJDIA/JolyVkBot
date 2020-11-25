package jolyjdia.vk.sdk.actions;

import jolyjdia.vk.sdk.actors.UserActor;
import jolyjdia.vk.sdk.client.VkApiClient;
import jolyjdia.vk.sdk.queries.status.StatusGetQuery;
import jolyjdia.vk.sdk.queries.status.StatusSetQuery;

public class StatusAction extends AbstractAction {
    public StatusAction(VkApiClient vkApiClient) {
        super(vkApiClient);
    }
    public StatusGetQuery get(UserActor actor) {
        return new StatusGetQuery(client, actor);
    }

    public StatusSetQuery set(UserActor actor) {
        return new StatusSetQuery(client, actor);
    }
}
