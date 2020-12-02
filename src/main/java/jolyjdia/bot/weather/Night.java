package jolyjdia.bot.weather;

import com.google.gson.annotations.SerializedName;

public class Night {
    @SerializedName(value = "IconPhrase")
    private String iconPhrase;

    public String getIconPhrase() {
        return iconPhrase;
    }

    @Override
    public String toString() {
        return "Night{" +
                "iconPhrase='" + iconPhrase + '\'' +
                '}';
    }
}