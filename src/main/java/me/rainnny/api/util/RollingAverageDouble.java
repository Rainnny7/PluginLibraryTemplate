package me.rainnny.api.util;

import lombok.Getter;

import java.util.Arrays;

/**
 * Credits: https://github.com/funkemunky/Atlas/
 */
public class RollingAverageDouble {
    private final int size;
    private double[] elements;

    @Getter private double average;
    private int index;

    public RollingAverageDouble(int size, double initial) {
        this.size = size;
        elements = new double[size];
        average = initial;
        initial/= size;
        Arrays.fill(elements, initial);
    }

    public void add(double value) {
        value/= size;
        average-= elements[index];
        average+= value;
        elements[index] = value;
        index = (index + 1) % size;
        if (Double.isNaN(average)) {
            elements = new double[size];
            average = 0D;
            index = 0;
        }
    }

    public void cleanup() {
        Arrays.fill(elements, 0D);
        average = 0D;
    }
}