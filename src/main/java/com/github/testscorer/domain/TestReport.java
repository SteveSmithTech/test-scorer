package com.github.testscorer.domain;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class TestReport implements Iterable<TestResult> {
    private final SortedSet<TestResult> testResults;

    public TestReport(List<TestResult> testResults) {
        this.testResults = new TreeSet<>(testResults);
    }

    LocalDateTime getRunTime() { return testResults.first().getRunTime(); }

    @Override
    public Iterator<TestResult> iterator() {
        return testResults.iterator();
    }
}
