package jolyjdia.bot;

import jolyjdia.bot.commands.Command;
import jolyjdia.bot.commands.CommandLabel;
import jolyjdia.bot.commands.ConsumerCommand;
import jolyjdia.bot.commands.defaults.UtilsCommands;
import jolyjdia.bot.events.*;
import jolyjdia.bot.objects.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

public final class BotManager {
    private final Set<HandlerEvent> listeners = new TreeSet<>(new Comparator<HandlerEvent>() {
        @Override
        public int compare(HandlerEvent o1, HandlerEvent o2) {
            return o2.compareTo(o1.priority);
        }
    });
    private final Set<HandlerCommand> commands = new HashSet<>();

    public BotManager() {
        registerCommand(new UtilsCommands());
    }

    public void registerEvent(Listener listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (!method.isAnnotationPresent(EventLabel.class)) {
                continue;
            }
            Class<?> parameter = method.getParameterTypes()[0];
            if (!Event.class.isAssignableFrom(parameter)) {
                continue;
            }
            EventLabel label = method.getAnnotation(EventLabel.class);
            listeners.add(new HandlerEvent(event -> {
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
        for (Method method : command.getClass().getMethods()) {
            if (!method.isAnnotationPresent(CommandLabel.class)) {
                continue;
            }
            if (method.getParameterTypes().length > 0) {
                continue;
            }
            CommandLabel label = method.getAnnotation(CommandLabel.class);
            HandlerCommand handler = new HandlerCommand(() -> {
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

    public Set<HandlerEvent> getListeners() {
        return listeners;
    }

    public void unregisterAllEvents() {
        listeners.clear();
    }

    public void registerAllEvents(Iterable<? extends Listener> iterable) {
        iterable.forEach(this::registerEvent);
    }

    public Set<HandlerCommand> getRegisteredCommands() {
        return commands;
    }

    public void unregisterAllCommands() {
        commands.clear();
    }

    public void registerAllCommands(Iterable<? extends ConsumerCommand> iterable) {
        iterable.forEach(this::registerCommand);
    }

    public static class HandlerEvent implements Comparable<EventPriority>, Consumer<Event> {
        final Consumer<? super Event> consumer;
        final EventPriority priority;
        final boolean ignoreCancelled;

        HandlerEvent(Consumer<? super Event> consumer, EventPriority priority, boolean ignoreCancelled) {
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
            HandlerEvent handler = (HandlerEvent) o;
            return Objects.equals(consumer, handler.consumer);
        }

        @Override
        public int hashCode() {
            return consumer != null ? consumer.hashCode() : 0;
        }
    }
    public static class HandlerCommand extends Command {
        private final Runnable runnable;
        private final ConsumerCommand consumer;
        private final int minArg, maxArg;

        HandlerCommand(Runnable runnable, ConsumerCommand consumer, String[] alias, String args, String desc, int minArg, int maxArg) {
            super(alias[0], args, desc);
            this.runnable = runnable;
            this.consumer = consumer;
            this.minArg = minArg;
            this.maxArg = maxArg;
            setAlias(alias);
        }

        @Override
        public void execute(User sender, String[] args) {
            if(hasPermission(sender)) {
                if ((args.length >= minArg && args.length <= maxArg) || maxArg == -1) {
                    consumer.accept(sender, args);
                    runnable.run();
                } else {
                    if (!getUseCommand().isEmpty()) {
                        sender.sendMessage(getUseCommand());
                    }
                }
            }
        }
    }
}