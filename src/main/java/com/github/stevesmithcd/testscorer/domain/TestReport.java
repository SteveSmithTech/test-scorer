package com.github.stevesmithcd.testscorer.domain;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class TestReport implements Iterable<Test> {
    private final String name;
    private final LocalDateTime creationTimestamp;
    private final List<Test> tests;

    public TestReport(String name, LocalDateTime creationTimestamp, List<Test> tests) {
        this.name = name;
        this.creationTimestamp = creationTimestamp;
        this.tests = tests;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public Iterator<Test> iterator() {
        return tests.iterator();
    }
}
