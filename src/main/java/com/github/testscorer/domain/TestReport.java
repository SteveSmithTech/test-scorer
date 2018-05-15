package com.github.testscorer.domain;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Optional.*;
import static java.util.Optional.empty;

public class TestReport implements Iterable<TestResult>, Comparable<TestReport> {
    private final SortedSet<TestResult> testResults;
    private final LocalDateTime runTime;

    public TestReport(List<TestResult> testResults) {
        this(testResults, empty());
    }

    public TestReport(List<TestResult> testResults,
                      LocalDateTime runTime) {
        this(testResults, of(runTime));
    }

    private TestReport(List<TestResult> testResults,
                       Optional<LocalDateTime> runTime) {
        if (testResults.isEmpty()) throw new IllegalArgumentException("Test report cannot be empty");

        this.testResults = new TreeSet<>(testResults);
        this.runTime = runTime.orElse(this.testResults.first().getRunTime());
    }

    LocalDateTime getRunTime() { return runTime; }

    @Override
    public Iterator<TestResult> iterator() {
        return testResults.iterator();
    }

    @Override
    public int compareTo(TestReport o) { return o.getRunTime().compareTo(getRunTime()); }
}
