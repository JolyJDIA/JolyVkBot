package jolyjdia.bot.commands;

import jolyjdia.bot.objects.User;

import java.util.Objects;
import java.util.Set;

public abstract class Command {
    protected final String name;
    protected String description;
    protected String arguments;
    protected Set<String> alias;
    protected String permission;
    protected String noPermissionMessage;

    protected Command(String name) {
        this.name = name;
    }

    protected Command(String name, String description) {
        this(name);
        this.description = description;
    }

    protected Command(String name, String arguments, String description) {
        this(name, description);
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
    public final Set<String> getAlias() {
        return alias;
    }

    /**
     *
     * @param alias Множество псевдонимов для этой команды
     */
    public final void setAlias(String... alias) {
        this.alias = Set.of(alias);
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
        return (alias != null && !alias.isEmpty()) && alias.stream().anyMatch(e -> e.equalsIgnoreCase(s2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}