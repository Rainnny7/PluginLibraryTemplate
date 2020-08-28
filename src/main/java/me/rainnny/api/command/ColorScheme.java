package me.rainnny.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Braydon
 * Color schemes are for the built in help menus
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColorScheme {
    String primaryColor() default "§6";

    String secondaryColor() default "§f";

    String tertiaryColor() default "§7";
}