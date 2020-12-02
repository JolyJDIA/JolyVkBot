package jolyjdia.bot.weather;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName(value = "Minimum")
    private Minimum minimum;
    @SerializedName(value = "Maximum")
    private Maximum maximum;

    public Maximum getMaximum() {
        return maximum;
    }

    public Minimum getMinimum() {
        return minimum;
    }
    public static double toCelsius(int fahrenheit) {
        return -17.7777777777778 + 0.555555555555556*fahrenheit;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "minimum=" + minimum +
                ", maximum=" + maximum +
                '}';
    }
}