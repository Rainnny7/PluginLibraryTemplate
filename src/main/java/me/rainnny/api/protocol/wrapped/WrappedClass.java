package me.rainnny.api.protocol.wrapped;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.rainnny.api.protocol.Packet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A cleaned up version of the class from: http://github.com/funkemunky/Atlas
 */
@RequiredArgsConstructor
public class WrappedClass {
    @Getter private final Class<?> parent;

    public WrappedClass(Packet.Client packet) {
        this(packet.getName());
    }

    public WrappedClass(Packet.Server packet) {
        this(packet.getName());
    }

    @SneakyThrows
    public WrappedClass(String name) {
        parent = Class.forName(name);
    }

    @SneakyThrows
    public WrappedMethod getMethodByName(String name, Class<?>... params) {
        return new WrappedMethod(parent.getMethod(name, params));
    }

    public List<WrappedField> getDeclaredFields() {
        return Arrays.stream(parent.getDeclaredFields()).map(WrappedField::new).collect(Collectors.toList());
    }

    @SneakyThrows
    public WrappedMethod getMethodByReturnType(Class<?> type, int index) {
        List<Method> methodList = Arrays.stream(parent.getDeclaredMethods())
                .filter(method -> method.getReturnType().equals(type))
                .collect(Collectors.toList());
        if (methodList.size() == 0)
            throw new NoSuchMethodException("There are no methods in " + parent.getSimpleName() + " with return type " + type.getSimpleName());

        if (index > methodList.size() - 1)
            throw new IndexOutOfBoundsException("There are no methods at index " + index + " for class " + type.getSimpleName() + "!");
        Method method = methodList.get(index);
        methodList.clear();
        return new WrappedMethod(method);
    }

    @SneakyThrows
    public WrappedField getFieldByName(String name) {
        return new WrappedField(parent.getField(name));
    }

    @SneakyThrows
    public WrappedField getFieldByType(Class<?> type, int index) {
        List<Field> fieldList = Arrays.stream(parent.getDeclaredFields())
                .filter(field -> field.getType().equals(type))
                .collect(Collectors.toList());
        if (fieldList.size() == 0)
            throw new NoSuchFieldException("There are no fields in " + parent.getSimpleName() + " with return type " + type.getSimpleName());

        if (index > fieldList.size() - 1)
            throw new IndexOutOfBoundsException("There are no fields at index " + index + " for class " + type.getSimpleName() + "!");
        Field field = fieldList.get(index);
        fieldList.clear();
        return new WrappedField(field);
    }

    @SneakyThrows
    public WrappedConstructor getConstructor(Class<?>... types) {
        return new WrappedConstructor(parent.getDeclaredConstructor(types));
    }
}