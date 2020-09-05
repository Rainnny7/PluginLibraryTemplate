package me.rainnny.api.timing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.rainnny.api.util.RollingAverageDouble;

/**
 * A cleaned up version of the class from: http://github.com/funkemunky/Atlas
 * @originalAuthor https://github.com/DeprecatedLuke
 */
@RequiredArgsConstructor @Setter @Getter
public class Timing {
    private final String name;
    private long lastCall;
    private RollingAverageDouble rollingAverage = new RollingAverageDouble(40, 0D);
    private double standardDeviation;
    private long totalTime, call;
    private int calls;
}