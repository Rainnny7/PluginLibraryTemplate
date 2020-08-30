package me.rainnny.api.util;

import java.util.Random;

/**
 * @author Braydon
 */
public class NumberUtils {
    private static final Random random = new Random();

    /**
     * Get a random number between 0 and the provided max value
     * @param max - The highest value
     * @return a random number between 0 and the provided max value
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    /**
     * Get a random number between the provided lower and max values
     * @param lower - The lowest value
     * @param max - The highest value
     * @return a random number between the provided lower and max values
     */
    public static int getRandom(int lower, int max) {
        return random.nextInt((max - lower) + 1) + lower;
    }
}