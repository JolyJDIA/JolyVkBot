package jolyjdia.vk.sdk.objects.callback.longpoll;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NonNls;

import java.util.List;

public class GetLongPollEventsResponse {
    private Integer ts;
    private List<JsonObject> updates;

    public Integer getTs() {
        return ts;
    }

    public List<JsonObject> getUpdates() {
        return updates;
    }
    @Override
    public @NonNls String toString() {
        return "GetLongPollEventsResponse{" + "ts=" + ts +
                ", updates=" + updates +
                '}';
    }
}

