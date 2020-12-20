package jolyjdia.bot.commands;

import jolyjdia.bot.Bot;
import jolyjdia.bot.objects.User;

import java.util.Arrays;

public abstract class Command {
    private static String helpCommand;//lazy
    private final String name;
    protected final String[] alias;
    protected String description;
    protected String arguments;
    protected String permission;
    protected String noPermissionMessage;


    protected Command(String... alias) {
        if (alias.length == 0) {
            throw new IllegalArgumentException("А где cumАнда?");
        }
        this.alias = alias;
        this.name = alias[0];
    }

    protected Command(String description, String[] alias) {
        this(alias);
        this.description = description;
    }

    protected Command(String arguments, String description, String[] alias) {
        this(description, alias);
        this.arguments = arguments;
    }

    /**
     * @return Название команды
     */
    public final String getName() {
        return name;
    }

    /**
     * Устанавливает право на команду
     * @param permission Название права
     * @param noPermissionMessage Сообщение отказа в правах
     */
    public final void setPermission(String permission, String noPermissionMessage) {
        this.permission = permission;
        this.noPermissionMessage = noPermissionMessage;
    }

    /**
     * Выполняет команду
     * @param sender Пользователь, который ввел команду
     * @param args Аргументы команд
     */
    public abstract void execute(User sender, String[] args);

    /**
     * @return Использование команды
     */
    public final String getArguments() {
        return arguments;
    }
    /**
     * @return Описание команды
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @return Множество активных псевдонимов этой команды
     */
    public final String[] getAlias() {
        return alias;
    }

    public final String getPermission() {
        return permission;
    }
    /**
     * Разрешение имеется
     * @param user Пользователь
     * @return true, если пользователь может использовать, в противном случае false
     */
    public final boolean hasPermission(User user) {
        if (permission == null || permission.isEmpty()) {
            return true;
        }
        boolean hasPermission = user.hasPermission(permission);
        if(!hasPermission) {
            user.sendMessage(noPermissionMessage);
        }
        return hasPermission;
    }

    /**
     * Разрешение отсутствует
     * @param user Пользователь
     * @return true Если у пользователя нет разрешения
     */
    public final boolean noPermission(User user) {
        return !hasPermission(user);
    }

    protected final String getUseCommand() {
        return '/' + name + (arguments != null && !arguments.isEmpty() ? ' ' + arguments : "");
    }
    public final boolean equalsCommand(String s2) {
        for (String cmd : alias) {
            if (cmd.equalsIgnoreCase(s2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return name.equalsIgnoreCase(command.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    static String getHelpCommand() {
        if (helpCommand == null) {
            StringBuilder builder = new StringBuilder();
            for (Command cmd : Bot.getBotManager().getRegisteredCommands()) {
                if (cmd.getDescription() != null && !cmd.getDescription().isEmpty()) {
                    builder.append('/').append(cmd.getName());// /cmd - дададая | /cmd <да-да я> - описание
                    if (cmd.getArguments() != null && !cmd.getArguments().isEmpty()) {
                        builder.append(' ').append(cmd.getArguments());
                    }
                    builder.append(" - ").append(cmd.getDescription()).append('\n');
                }
            }
            return helpCommand = builder.toString();
        }
        return helpCommand;
    }

    @Override
    public String toString() {
        return "Command{" +
                "description='" + description + '\'' +
                ", arguments='" + arguments + '\'' +
                ", alias=" + Arrays.toString(alias) +
                ", permission='" + permission + '\'' +
                ", noPermissionMessage='" + noPermissionMessage + '\'' +
                '}';
    }
}