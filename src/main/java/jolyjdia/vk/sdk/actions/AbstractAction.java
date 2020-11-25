package jolyjdia.vk.sdk.actions;

import jolyjdia.vk.sdk.client.VkApiClient;

public class AbstractAction {
    protected final VkApiClient client;

    public AbstractAction(VkApiClient client) {
        this.client = client;
    }
}