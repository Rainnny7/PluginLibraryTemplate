package me.rainnny.api.menu;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Braydon
 * This annotation holds menu information, such as the
 * title and rows
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuInfo {
    String title();

    int size() default 1;
}