package jolyjdia.bot.commands;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandLabel {
    String[] alias();
    String usage() default "";//тут путь к локализации сделать
    String desc() default "";
    int minArg() default 0;
    int maxArg() default -1;
    String permission() default "";
    String noPermissionMsg() default "У Вас нет прав!";
}
