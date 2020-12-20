package jolyjdia.bot;

import jolyjdia.bot.commands.Command;
import jolyjdia.bot.commands.CommandLabel;
import jolyjdia.bot.commands.ConsumerCommand;
import jolyjdia.bot.commands.defaults.UtilsCommands;
import jolyjdia.bot.commands.defaults.WeatherCommands;
import jolyjdia.bot.events.*;
import jolyjdia.bot.objects.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

public final class BotManager {
    private final Set<HandlerEvent> listeners = new TreeSet<>((o1, o2) -> o2.compareTo(o1.priority));
    private final Set<Command> commands = new HashSet<>();

    public BotManager() {
        registerCommand(new UtilsCommands());
        registerCommand(new WeatherCommands());
    }

    public void registerEvent(Listener listener) {
        Class<?> clazz = listener.getClass();
        for (Method method : clazz.getMethods()) {
            if (!method.isAnnotationPresent(EventLabel.class)) {
                continue;
            }
            Class<?> parameter = method.getParameterTypes()[0];
            if (!Event.class.isAssignableFrom(parameter)) {
                continue;
            }
            EventLabel label = method.getAnnotation(EventLabel.class);
            listeners.add(new HandlerEvent(clazz, event -> {
                if (!event.getClass().isAssignableFrom(parameter)) {
                    return;
                }
                try {
                    method.invoke(listener, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }, label.priority(), label.ignoreCancelled()));
        }
    }
    public void registerCommand(ConsumerCommand command) {
        Class<?> clazz = command.getClass();//todo: проверять на зареганную
        for (Method method : clazz.getMethods()) {
            if (!method.isAnnotationPresent(CommandLabel.class)) {
                continue;
            }
            if (method.getParameterTypes().length > 0) {
                continue;
            }
            CommandLabel label = method.getAnnotation(CommandLabel.class);
            HandlerCommand handler = new HandlerCommand(clazz, () -> {
                try {
                    method.invoke(command);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }, command, label.alias(), label.usage(), label.desc(), label.minArg(), label.maxArg());
            handler.setPermission(label.permission(), label.noPermissionMsg());
            commands.add(handler);
        }
    }
    public void registerCommand(Command command) {
        commands.add(command);
    }

    public Set<HandlerEvent> getListeners() {
        return listeners;
    }

    public void unregisterAllEvents() {
        listeners.clear();
    }

    public void registerAllEvents(Iterable<? extends Listener> iterable) {
        iterable.forEach(this::registerEvent);
    }

    public Set<Command> getRegisteredCommands() {
        return commands;
    }

    public void unregisterAllCommands() {
        commands.clear();
    }

    public void registerAllCommands(Iterable<? extends ConsumerCommand> iterable) {
        iterable.forEach(this::registerCommand);
    }

    final static class HandlerEvent implements Comparable<EventPriority>, Consumer<Event> {
        final Class<?> clazz;
        final Consumer<? super Event> consumer;
        final EventPriority priority;
        final boolean ignoreCancelled;

        HandlerEvent(Class<?> clazz, Consumer<? super Event> consumer, EventPriority priority, boolean ignoreCancelled) {
            this.clazz = clazz;
            this.consumer = consumer;
            this.priority = priority;
            this.ignoreCancelled = ignoreCancelled;
        }

        @Override
        public final void accept(Event event) {
            if (Cancellable.class.isAssignableFrom(event.getClass())) {//TODO:
                if (((Cancellable) event).isCancelled() && ignoreCancelled) {
                    return;
                }
            }
            consumer.accept(event);
        }

        @Override
        public final int compareTo(EventPriority handler) {
            return Integer.compare(priority.getSlot(), handler.getSlot());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HandlerEvent that = (HandlerEvent) o;
            return clazz == that.clazz;
        }

        @Override
        public int hashCode() {
            return clazz.hashCode();
        }
    }
    final static class HandlerCommand extends Command {
        private final Class<?> clazz;
        private final Runnable runnable;
        private final ConsumerCommand consumer;
        private final int minArg, maxArg;

        HandlerCommand(Class<?> clazz, Runnable runnable, ConsumerCommand consumer, String[] alias, String args, String desc, int minArg, int maxArg) {
            super(args, desc, alias);
            this.clazz = clazz;
            this.runnable = runnable;
            this.consumer = consumer;
            this.minArg = minArg;
            this.maxArg = maxArg;
        }

        @Override
        public void execute(User sender, String[] args) {
            if(hasPermission(sender)) {
                int lenArg = args.length;
                if((lenArg >= minArg && lenArg <= maxArg) || maxArg == -1) {
                    consumer.accept(sender, args);
                    runnable.run();
                } else {
                    if (!getUseCommand().isEmpty()) {
                        sender.sendMessage(getUseCommand());
                    }
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            HandlerCommand that = (HandlerCommand) o;
            return clazz == that.clazz;
        }

        @Override
        public int hashCode() {
            return clazz.hashCode();
        }
    }
}