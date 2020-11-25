package jolyjdia.vk.sdk.actions;

import jolyjdia.vk.sdk.client.VkApiClient;
import jolyjdia.vk.sdk.queries.longpoll.GetLongPollEventsQuery;

public class LongPollAction extends AbstractAction {

    public LongPollAction(VkApiClient client) {
        super(client);
    }

    public GetLongPollEventsQuery getEvents(String url, String key, Integer ts) {
        return new GetLongPollEventsQuery(client, url, key, ts);
    }
}
