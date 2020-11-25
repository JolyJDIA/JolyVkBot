package jolyjdia.bot.utils.timeformat;

import java.time.Duration;

@FunctionalInterface
public interface CallableDuration {
    long call(Duration duration);
}