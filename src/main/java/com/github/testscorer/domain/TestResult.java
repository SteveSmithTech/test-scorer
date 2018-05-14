package com.github.testscorer.domain;

import java.time.LocalDateTime;

import static java.lang.String.format;

public final class TestResult implements Comparable<TestResult> {
    private final String name;
    private LocalDateTime runTime;
    private final Result result;

    public TestResult(String name, LocalDateTime runTime, Result result) {
        this.name = name;
        this.runTime = runTime;
        this.result = result;
    }

    String getName() {
        return name;
    }

    LocalDateTime getRunTime() {
        return runTime;
    }

    boolean unsuccessful() {
        return result.unsuccessful();
    }

    @Override
    public int compareTo(TestResult o) {
        return runTime.equals(o.runTime) ? name.compareTo(o.name) : runTime.compareTo(o.runTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestResult that = (TestResult) o;
        return (name != null ? name.equals(that.name) : that.name == null) && (runTime != null ? runTime.equals(that.runTime) : that.runTime == null) && result == that.result;
    }

    @Override
    public int hashCode() {
        int hashCode = name != null ? name.hashCode() : 0;
        hashCode = 31 * hashCode + (runTime != null ? runTime.hashCode() : 0);
        hashCode = 31 * hashCode + (result != null ? result.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return format("%s %s %s", name, runTime, result);
    }
}
