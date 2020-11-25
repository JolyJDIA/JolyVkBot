package jolyjdia.vk.sdk.callback;

import jolyjdia.vk.sdk.objects.enums.MessageType;
import org.jetbrains.annotations.NonNls;

public class ConfirmationMessage {
    private MessageType type;
    private Integer group_id;
    private String secret;

    public MessageType getType() {
        return type;
    }

    public Integer getGroupId() {
        return group_id;
    }


    public String getSecret() {
        return secret;
    }

    @Override
    public @NonNls String toString() {
        return "ConfirmationMessage{" +
                "type=" + type +
                ", group_id=" + group_id +
                ", secret='" + secret + '\'' +
                '}';
    }
}
