package me.rainnny.api.network.wrapped;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

/**
 * A cleaned up version of the class from: http://github.com/funkemunky/Atlas
 */
public class WrappedConstructor {
    private final Constructor<?> constructor;

    public WrappedConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public <T> T newInstance(Object... objects) {
        return (T) constructor.newInstance(objects);
    }
}