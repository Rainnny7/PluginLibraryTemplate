package me.rainnny.api.network.wrapped;

import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

/**
 * A cleaned up version of the class from: http://github.com/funkemunky/Atlas
 */
@Getter
public class WrappedMethod {
    private final Method method;

    public WrappedMethod(Method method) {
        this.method = method;
        method.setAccessible(true);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public <T> T invoke(Object instance, Object... params) {
        return (T) method.invoke(instance, params);
    }
}