package jolyjdia.bot.commands.defaults;

import jolyjdia.bot.commands.CommandLabel;
import jolyjdia.bot.commands.ConsumerCommand;
import jolyjdia.bot.utils.timeformat.TemporalDuration;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UtilsCommands extends ConsumerCommand {
    public static final String NEW_YEAR = "\uD83D\uDD25\uD83E\uDD76Новый Год через: %s\uD83E\uDD76\uD83D\uDD25";

    @CommandLabel(alias = {"нг", "весна", "лето"})
    public void happy() {
        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("нг")) {
                sender.sendMessage(String.format(NEW_YEAR, TemporalDuration.of(1, 1, 0,0)));
            } else if(args[0].equalsIgnoreCase("весна")) {
                sender.sendMessage(TemporalDuration.of(3, 1, 0, 0).toFormat());
            } else if(args[0].equalsIgnoreCase("лето")) {
                sender.sendMessage(TemporalDuration.of(6, 1, 0, 0).toFormat());
            }
        }
    }
    private static final String KEY = "";
            //"https://translate.yandex.net/api/v1.5/tr.json/translate?key="+ Bot.getConfig().getProperty("translateKey");

    private static URL url;
    static {
        try {
            url = new URL(KEY);
        } catch (MalformedURLException e) {
            url = null;
        }
    }
    public static String translate(String lang, String input) throws IOException {
        StringBuilder builder = new StringBuilder();
        if(url == null) {
            return "URL Error";
        }
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
            dataOutputStream.writeBytes("text=" + URLEncoder.encode(input, StandardCharsets.UTF_8) + "&lang=" + lang);
            try (InputStream response = connection.getInputStream(); ByteArrayOutputStream result = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = response.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                String json = result.toString(StandardCharsets.UTF_8);//StandardCharsets.UTF_8
                builder.append(json, json.indexOf('[') + 2, json.indexOf(']') - 1);

            }
        }
        return builder.toString();
    }
}