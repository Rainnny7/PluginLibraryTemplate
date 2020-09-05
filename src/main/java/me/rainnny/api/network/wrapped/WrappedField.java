package me.rainnny.api.network.wrapped;

import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

/**
 * A cleaned up version of the class from: http://github.com/funkemunky/Atlas
 */
@Getter
public class WrappedField {
    private final Field field;

    private final String name;
    private final Class<?> type;

    public WrappedField(Field field) {
        this.field = field;

        field.setAccessible(true);
        name = field.getName();
        type = field.getType();
    }

    @SneakyThrows
    public void set(Object instance, Object value) {
        field.set(instance, value);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public <T> T get(Object instance) {
        return (T) field.get(instance);
    }
}