package me.rainnny.api.storage.type.flatFile;

import java.util.List;

/**
 * @author Braydon
 * All elements inside of a {@code FlatFileContainer} should
 * have it's values implement this token. This token helps
 * identify keys inside of the provided file in the container
 */
public interface FlatFileToken {
    List<String> getKeys();

    Object load(String key, FlatFileEntries entries);

    FlatFileEntries save();
}