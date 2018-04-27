package com.github.stevesmithcd.testscorer.domain;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Test implements Comparable<Test> {
    private final String name;
    private final LocalDateTime runTime;
    private final Duration duration;
    private final Result result;

    public Test(String name, LocalDateTime runTime, Duration duration, Result result) {
        this.name = name;
        this.runTime = runTime;
        this.duration = duration;
        this.result = result;
    }

    @Override
    public int compareTo(Test o) {
        return runTime.equals(o.runTime) ? name.compareTo(o.name) : runTime.compareTo(o.runTime);
    }
}
