package com.github.stevesmithcd.testscorer.domain;

import java.time.LocalDateTime;

public final class Test implements Comparable<Test> {
    private final String name;
    private LocalDateTime runTime;
    private final Result result;

    public Test(String name, LocalDateTime runTime, Result result) {
        this.name = name;
        this.runTime = runTime;
        this.result = result;
    }

    String getName() {
        return name;
    }

    public LocalDateTime getRunTime() {
        return runTime;
    }

    boolean unsuccessful() {
        return result.unsuccessful();
    }


    @Override
    public int compareTo(Test o) {
        return runTime.equals(o.runTime) ? name.compareTo(o.name) : runTime.compareTo(o.runTime);
    }

}
