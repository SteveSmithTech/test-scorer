package com.github.stevesmithcd.testscorer.domain;

import java.time.Duration;
import java.util.Date;

public final class Test implements Comparable<Test> {
    private final String name;
    private final Date timestamp;
    private final Duration duration;
    private final Result result;

    public Test(String name, Date timestamp, Duration duration, Result result) {
        this.name = name;
        this.timestamp = timestamp;
        this.duration = duration;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Duration getDuration() {
        return duration;
    }

    public Result getResult() {
        return result;
    }


    @Override
    public int compareTo(Test o) {
        if (timestamp.equals(o.timestamp)) return name.compareTo(o.name);

        return timestamp.before(o.timestamp) ? -1 : 1;
    }
}
