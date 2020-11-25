package jolyjdia.vk.sdk.objects.base;

import org.jetbrains.annotations.NonNls;

public class RequestParam {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    @Override
    public @NonNls String toString() {
        return "RequestParam{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
