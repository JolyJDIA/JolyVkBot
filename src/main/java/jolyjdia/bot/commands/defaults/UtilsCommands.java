package jolyjdia.bot.commands.defaults;

import com.sun.management.OperatingSystemMXBean;
import jolyjdia.bot.commands.CommandLabel;
import jolyjdia.bot.commands.ConsumerCommand;
import jolyjdia.bot.utils.timeformat.TemporalDuration;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UtilsCommands extends ConsumerCommand {
    private static final OperatingSystemMXBean BEAN = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    @CommandLabel(alias = {"нг", "весна", "лето"})
    public void happy() {
        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("нг")) {
                sender.sendMessage(getNewYear());
            } else if(args[0].equalsIgnoreCase("весна")) {
                sender.sendMessage(TemporalDuration.of(3, 1, 0, 0).toFormat());
            } else if(args[0].equalsIgnoreCase("лето")) {
                sender.sendMessage(TemporalDuration.of(6, 1, 0, 0).toFormat());
            }
        }
    }
    public static String getNewYear() {
        return "\uD83D\uDE44Новый Год через: "+TemporalDuration.of(1, 1, 0,0)+"\uD83D\uDE44";
    }
    @CommandLabel(alias = {"memory", "lag"}, permission = "shallwe.lag")
    public void lag() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        sender.sendMessage("\nJava version: " + Runtime.version() +
                "\nПамять:" +
                "\n Max: " + runtime.maxMemory() / 1048576L + " МB" +
                "\n Total: " + totalMemory / 1048576L + " МB" +
                "\n Use: " + (totalMemory - freeMemory) / 1048576L + " МB" +
                " (" + freeMemory / 1048576L + " MB свободно)" +
                "\nПроцессы:" +
                "\n Ядер: " + runtime.availableProcessors() +
                "\n Использовано сервером: " + Math.round(BEAN.getProcessCpuLoad() * 100) + '%' +
                "\n Использовано системой: " + Math.round(BEAN.getCpuLoad() * 100) + '%' +
                "\n Активные потоки: " + Thread.activeCount() +
                "\n ");
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