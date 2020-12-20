package jolyjdia.bot.commands.defaults;

import com.sun.management.OperatingSystemMXBean;
import jolyjdia.bot.Bot;
import jolyjdia.bot.commands.CommandLabel;
import jolyjdia.bot.commands.ConsumerCommand;
import jolyjdia.bot.scheduler.Task;
import jolyjdia.bot.utils.timeformat.TemporalDuration;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class UtilsCommands extends ConsumerCommand {
    private static final OperatingSystemMXBean BEAN = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private static final DecimalFormat format = new DecimalFormat("#0.00");

    @CommandLabel(alias = "нг", desc = "расчет времени до Нового года")
    public void newYear() {
        sender.sendMessage(getNewYear());
        //        if (args.length == 1) {
        //            if(args[0].equalsIgnoreCase("нг")) {
        //                sender.sendMessage(getNewYear());
        //            } else if(args[0].equalsIgnoreCase("весна")) {
        //                sender.sendMessage(TemporalDuration.of(3, 1, 0, 0).toFormat());
        //            } else if(args[0].equalsIgnoreCase("лето")) {
        //                sender.sendMessage(TemporalDuration.of(6, 1, 0, 0).toFormat());
        //            }
    }
    public static String getNewYear() {
        return "\uD83D\uDE44До Нового года осталось: "+TemporalDuration.of(1, 1, 0,0)+"\uD83D\uDE44";
    }
    @CommandLabel(alias = {"memory", "lag"}, desc = "состояние", permission = "shallwe.lag")
    public void lag() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        StringJoiner joiner = new StringJoiner(" ");
        for (double u : Bot.getScheduler().recentTps) {
            joiner.add(format.format(u));
        }
        sender.sendMessage("\nJava version: " + Runtime.version() +
                "\nTPS from last:" +
                "\n->1min 5min 15min:" +
                "\n->"+ joiner.toString() +
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
    private Task raid;
    @CommandLabel(alias = "raid", minArg = 1, usage = "<сообщение>", desc = "рейд")
    public void raid() {
        if (raid != null) {
            sender.sendMessage("Рейд уже где-то идет");
        }
        String arg = args[1] + ' ';
        int copy = 1000 / arg.length();
        final String msg = arg.repeat(copy);
        long start = System.currentTimeMillis();
        raid = Bot.getScheduler().runRepeatingSyncTaskAfter(() -> {
            sender.sendMessage(msg);
            if (System.currentTimeMillis() - start > TimeUnit.SECONDS.toMillis(20)) {
                raid.cancel0();
                raid = null;
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
    }
}