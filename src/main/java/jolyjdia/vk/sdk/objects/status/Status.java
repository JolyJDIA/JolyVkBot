package jolyjdia.vk.sdk.objects.status;


import jolyjdia.vk.sdk.objects.audio.Audio;
import org.jetbrains.annotations.NonNls;

public class Status {
    private Audio audio;
    private String text;

    public Audio getAudio() {
        return audio;
    }

    public String getText() {
        return text;
    }

    @Override
    public @NonNls String toString() {
        return "Status{" +
                "audio=" + audio +
                ", text='" + text + '\'' +
                '}';
    }
}
