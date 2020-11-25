package jolyjdia.vk.sdk.client;

import jolyjdia.vk.sdk.actions.GroupsLongPollAction;
import jolyjdia.vk.sdk.actions.LongPollAction;
import jolyjdia.vk.sdk.actions.MessagesAction;
import jolyjdia.vk.sdk.actions.StatusAction;

public class VkApiClient {
    private static final String API_VERSION = "5.126";
    private static final String API_ADDRESS = "https://api.vk.com/method/";
    private static final String OAUTH_ENDPOINT = "https://oauth.vk.com/";

    private final TransportClient transportClient;
    private final String apiEndpoint;
    private final String oauthEndpoint;
    private final StatusAction statusAction;
    private final MessagesAction messagesAction;
    private final LongPollAction longPollAction;
    private final GroupsLongPollAction groupsLongPollAction;

    public VkApiClient(TransportClient transportClient) {
        this.transportClient = transportClient;

        String host;
        apiEndpoint = (host = System.getProperty("api.host")) != null && !host.isEmpty()
                ? "https://" + System.getProperty("api.host") + "/method/"
                : API_ADDRESS;
        String oauthHost;
        oauthEndpoint = (oauthHost = System.getProperty("oauth.host")) != null && !oauthHost.isEmpty()
                ? "https://" + System.getProperty("oauth.host") + "/"
                : OAUTH_ENDPOINT;
        this.statusAction = new StatusAction(this);
        this.messagesAction = new MessagesAction(this);
        this.longPollAction = new LongPollAction(this);
        this.groupsLongPollAction = new GroupsLongPollAction(this);
    }

    public TransportClient getTransportClient() {
        return transportClient;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public String getOAuthEndpoint() {
        return oauthEndpoint;
    }

    public static String getVersion() {
        return API_VERSION;
    }

    public StatusAction status() {
        return statusAction;
    }

    public MessagesAction messages() {
        return messagesAction;
    }
    public LongPollAction longPoll() {
        return longPollAction;
    }

    public GroupsLongPollAction groupsLongPoll() {
        return groupsLongPollAction;
    }
}
