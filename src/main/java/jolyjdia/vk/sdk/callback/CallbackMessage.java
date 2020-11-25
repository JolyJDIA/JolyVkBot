package jolyjdia.vk.sdk.callback;

import jolyjdia.vk.sdk.objects.enums.MessageType;
import org.jetbrains.annotations.NonNls;

public class CallbackMessage<T> {
    private MessageType type;
    private Integer group_id;
    private T object;
    private String secret;
    private String event_id;

    public MessageType getType() {
        return type;
    }

    public Integer getGroupId() {
        return group_id;
    }

    public T getObject() {
        return object;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public @NonNls String toString() {
        return "CallbackMessage{" + "type=" + type +
                ", groupId=" + group_id +
                ", object=" + object +
                ", secret='" + secret + '\'' +
                ", event_id='" + event_id + '\'' +
                '}';
    }
}
