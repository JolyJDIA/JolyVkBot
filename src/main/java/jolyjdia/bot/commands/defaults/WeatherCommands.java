package jolyjdia.bot.commands.defaults;

import com.google.gson.annotations.SerializedName;
import jolyjdia.bot.Bot;
import jolyjdia.bot.commands.CommandLabel;
import jolyjdia.bot.commands.ConsumerCommand;
import jolyjdia.bot.utils.StringBind;
import jolyjdia.bot.weather.DailyForecasts;
import jolyjdia.bot.weather.Temperature;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class WeatherCommands extends ConsumerCommand {
    private static final String apiKey = Bot.getConfig().getProperty("weatherKey");
    private static final String url = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/323031?apikey="+apiKey+"&language=ru";
    private String weather; long start;

    @CommandLabel(alias = {"погода", "weather"})
    public void weather() {
        if (weather == null || (System.currentTimeMillis() - start) < TimeUnit.MILLISECONDS.toDays(1)) {
            System.out.println("new");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            Bot.getHttpClient().get(request).thenAccept(e -> {
                start = System.currentTimeMillis();
                Test test = StringBind.fromJson(e.body(), Test.class);
                DailyForecasts df = test.dailyForecasts[0];
                int min = df.getTemperature().getMinimum().getValue();
                int max = df.getTemperature().getMaximum().getValue();
                sender.sendMessage((weather = "Погода в Горловке(пока что так):\nДата: " + df.getDate() +
                        "\nMin temperature: " + min + 'F' + " (" + Temperature.toCelsius(min) + "C)" +
                        "\nMax temperature: " + max + 'F' + " (" + Temperature.toCelsius(max) + "C)" +
                        "\nДнем: " + df.getDay().getIconPhrase() +
                        "\nНочью: " + df.getNight().getIconPhrase()
                ));
            });
        } else {
            sender.sendMessage(weather);
        }
    }

    public static class Test {
        @SerializedName(value = "DailyForecasts")
        private DailyForecasts[] dailyForecasts;

        @Override
        public String toString() {
            return "Test{" +
                    "dailyForecasts=" + Arrays.toString(dailyForecasts) +
                    '}';
        }
    }
}