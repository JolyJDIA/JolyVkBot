package jolyjdia.bot.commands.defaults;

import com.sun.management.OperatingSystemMXBean;
import jolyjdia.bot.Bot;
import jolyjdia.bot.commands.CommandLabel;
import jolyjdia.bot.commands.ConsumerCommand;
import jolyjdia.bot.utils.timeformat.TemporalDuration;

import java.lang.management.ManagementFactory;
import java.util.StringJoiner;

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
        return "\uD83D\uDE44До Нового года осталось: "+TemporalDuration.of(1, 1, 0,0)+"\uD83D\uDE44";
    }
    @CommandLabel(alias = {"memory", "lag"}, permission = "shallwe.lag")
    public void lag() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        StringJoiner joiner = new StringJoiner(", ");
        for (double u : Bot.getScheduler().recentTps) {
            joiner.add(format(u));
        }
        sender.sendMessage("\nJava version: " + Runtime.version() +
                "\nTPS from last:" +
                "\n1m / 5m / 15m:" +
                "\n"+ joiner.toString() +
                "\nПамять:" +
                "\n->Max: " + runtime.maxMemory() / 1048576L + " МB" +
                "\n->Total: " + totalMemory / 1048576L + " МB" +
                "\n->Use: " + (totalMemory - freeMemory) / 1048576L + " МB" +
                " (" + freeMemory / 1048576L + " MB свободно)" +
                "\nПроцессы:" +
                "\n->Ядер: " + runtime.availableProcessors() +
                "\n->Использовано сервером: " + Math.round(BEAN.getProcessCpuLoad() * 100) + '%' +
                "\n->Использовано системой: " + Math.round(BEAN.getCpuLoad() * 100) + '%' +
                "\n->Активные потоки: " + Thread.activeCount() +
                '\n');
    }
    private String format(double tps) {
        String s = String.valueOf(Math.min(Math.round( tps * 100.0 ) / 100.0, 20.0));
        if (tps > 20.0) {
            s += "*";
        }
        return s;
    }
}