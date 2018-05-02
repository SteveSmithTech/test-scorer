package com.github.stevesmithcd.testscorer.domain;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class TestReport implements Iterable<Test> {
    private final SortedSet<Test> tests;

    public TestReport(List<Test> tests) {
        this.tests = new TreeSet<>(tests);
    }

    LocalDateTime getCreationTimestamp() { return tests.last().getRunTime(); }

    @Override
    public Iterator<Test> iterator() {
        return tests.iterator();
    }
}
