package jolyjdia.vk.sdk.objects.groups;

import org.jetbrains.annotations.NonNls;

public class LongPollServer {
    private String key;
    private String server;
    private String ts;

    public String getKey() {
        return key;
    }

    public String getServer() {
        return server;
    }

    public String getTs() {
        return ts;
    }

    @Override
    public @NonNls String toString() {
        return "LongPollServer{" +
                "key='" + key + '\'' +
                ", server='" + server + '\'' +
                ", ts='" + ts + '\'' +
                '}';
    }
}
