package me.rainnny.api.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Braydon
 * A storage element is an object which contains
 * a key and a value which is stored inside of the
 * {@code StorageContainer}
 */
@AllArgsConstructor @Getter
public class StorageElement {
    private final String key;
    private final Object value;

    @Override
    public String toString() {
        return "StorageElement{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}