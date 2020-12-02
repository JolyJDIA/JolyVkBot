package jolyjdia.bot.weather;

import com.google.gson.annotations.SerializedName;

public class DailyForecasts {
    @SerializedName(value = "Date")
    private String date;
    @SerializedName(value = "Temperature")
    private Temperature temperature;
    @SerializedName(value = "Day")
    private Day day;
    @SerializedName(value = "Night")
    private Night night;

    public String getDate() {
        return date;
    }

    public Night getNight() {
        return night;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Day getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "DailyForecasts{" +
                "date='" + date + '\'' +
                ", temperature=" + temperature +
                ", day=" + day +
                ", night=" + night +
                '}';
    }
}