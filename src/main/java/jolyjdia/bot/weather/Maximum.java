package jolyjdia.bot.weather;

import com.google.gson.annotations.SerializedName;

public class Maximum {
    @SerializedName(value = "Value")
    private int value;
    @SerializedName(value = "Unit")
    private String unit;
    @SerializedName(value = "UnitType")
    private int unitType;

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Maximum{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                ", unitType=" + unitType +
                '}';
    }
}