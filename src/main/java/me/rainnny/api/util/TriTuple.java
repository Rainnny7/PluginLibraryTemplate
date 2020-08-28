package me.rainnny.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Braydon
 * This is the same thing as {@code Tuple}, but instead has
 * 3 options; left, middle and right
 */
@AllArgsConstructor @Setter @Getter
public class TriTuple<A, B, C> {
    private final A left;
    private final B middle;
    private final C right;
}