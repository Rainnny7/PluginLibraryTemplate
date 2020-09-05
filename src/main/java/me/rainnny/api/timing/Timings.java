package me.rainnny.api.timing;

import lombok.Getter;
import me.rainnny.api.util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A cleaned up version of the class from: http://github.com/funkemunky/Atlas
 */
@Getter
public class Timings {
    private final List<Timing> timings = new ArrayList<>();

    private long started;
    private int totalCalls;
    private long lastSample, lastReset;

    public void start(String name) {
        Timing timing = getTiming(name);
        timing.setLastCall(System.nanoTime());
        if (started == 0L)
            started = System.currentTimeMillis();
    }

    public Map<String, Tuple<Integer, Double>> getResults(ResultType type) {
        Map<String, Tuple<Integer, Double>> results = new HashMap<>();
        for (Timing timing : timings) {
            switch (type) {
                case TOTAL: {
                    results.put(timing.getName(), new Tuple<>(timing.getCalls(), timing.getRollingAverage().getAverage() *
                            (timing.getCalls() / (double) totalCalls)));
                    break;
                }
                case AVERAGE: {
                    results.put(timing.getName(), new Tuple<>(timing.getCalls(), timing.getRollingAverage().getAverage()));
                    break;
                }
                case SAMPLES: {
                    results.put(timing.getName(), new Tuple<>(timing.getCalls(), (double) timing.getCall()));
                    break;
                }
                default: {
                    results.put(timing.getName(), new Tuple<>(timing.getCalls(), timing.getTotalTime() / (double) timing.getCalls()));
                }
            }
        }
        return results;
    }

    public void stop(String name) {
        stop(name, System.nanoTime());
    }

    public void stop(String name, long extense) {
        Timing timing = getTiming(name);
        long now = System.currentTimeMillis();
        if (now - lastReset < 100L)
            return;
        long time = (System.nanoTime() - timing.getLastCall()) - (System.nanoTime() - extense);
        timing.getRollingAverage().add(time);
        timing.setStandardDeviation(Math.abs(time - timing.getRollingAverage().getAverage()));
        timing.setTotalTime(timing.getTotalTime() + time);
        timing.setCall(time);
        timing.setCalls(timing.getCalls() + 1);
        totalCalls++;
        lastSample = now;
    }

    public void reset() {
        timings.clear();
        totalCalls = 0;
        lastSample = 0L;
        started = lastReset = System.currentTimeMillis();
    }

    public Timing getTiming(String name) {
        for (Timing timing : timings) {
            if (timing.getName().equalsIgnoreCase(name)) {
                return timing;
            }
        }
        Timing timing = new Timing(name);
        timings.add(timing);
        return timing;
    }
}