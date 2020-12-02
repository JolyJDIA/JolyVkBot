package jolyjdia.bot.weather;

import com.google.gson.annotations.SerializedName;

public class Day {
    @SerializedName(value = "IconPhrase")
    private String iconPhrase;

    public String getIconPhrase() {
        return iconPhrase;
    }

    @Override
    public String toString() {
        return "Day{" +
                "iconPhrase='" + iconPhrase + '\'' +
                '}';
    }
}