package jolyjdia.vk.sdk.queries.longpoll;

import jolyjdia.vk.sdk.client.LongPollQueryBuilder;
import jolyjdia.vk.sdk.client.VkApiClient;
import jolyjdia.vk.sdk.objects.callback.longpoll.GetLongPollEventsResponse;
import jolyjdia.vk.sdk.objects.enums.GetLongPollEventsActInfo;

public final class GetLongPollEventsQuery extends LongPollQueryBuilder<GetLongPollEventsResponse> {
    public GetLongPollEventsQuery(VkApiClient client, String url, String key, Integer ts) {
        super(url, client);
        unsafeParam("act", GetLongPollEventsActInfo.CHECK);
        unsafeParam("key", key);
        unsafeParam("ts", ts);
    }

    public GetLongPollEventsQuery waitTime(Integer value) {
        unsafeParam("wait", value);
        return this;
    }
}

