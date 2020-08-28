package me.rainnny.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Braydon
 * This class defines information for a command, like it's
 * name, aliases, etc
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name();

    String[] aliases() default {};

    String usage() default "";

    String description() default "";

    String permission() default "";

    boolean playersOnly() default false;

    boolean consoleOnly() default false;

    boolean displayHelp() default true;

    ColorScheme colorScheme() default @ColorScheme();
}