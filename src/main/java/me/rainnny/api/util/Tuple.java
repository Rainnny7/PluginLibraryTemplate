package me.rainnny.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Braydon
 * This is the same thing as {@code Pair}, but instead has
 * options to set both left and right fields
 */
@AllArgsConstructor @Setter @Getter
public class Tuple<A, B> {
    private final A left;
    private final B right;
}