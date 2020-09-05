package me.rainnny.api.storage.type.flatFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.rainnny.api.util.Tuple;

import java.util.List;

/**
 * @author Braydon
 * This class contains a list of keys and values which is used
 * to store what a flat file container element has. An example
 * key value layout would be as follows for an Account container:
 * rank: ADMIN
 */
@AllArgsConstructor @Getter
public class FlatFileEntries {
    private final List<Tuple<String, Object>> entries;

    public Object of(String key) {
        for (Tuple<String, Object> entry : entries) {
            if (entry.getLeft().equals(key)) {
                return entry.getRight();
            }
        }
        return null;
    }
}