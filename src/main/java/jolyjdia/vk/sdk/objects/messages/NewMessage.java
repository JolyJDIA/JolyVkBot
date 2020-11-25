package jolyjdia.vk.sdk.objects.messages;

import org.jetbrains.annotations.NonNls;

public class NewMessage {
    private Message message;

    public Message getMessage() {
        return message;
    }

    @Override
    public @NonNls String toString() {
        return "NewMessage{" +
                "message=" + message +
                '}';
    }
}
