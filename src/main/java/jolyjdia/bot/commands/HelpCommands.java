package jolyjdia.bot.commands;

import jolyjdia.bot.objects.User;

import java.util.Arrays;

public class HelpCommands extends Command {
    public HelpCommands() {
        super("помощь по командам", new String[]{"help"});
    }

    @Override
    public void execute(User sender, String[] args) {
        System.out.println(Arrays.toString(args));
        sender.sendMessage(Command.getHelpCommand());
    }
}
