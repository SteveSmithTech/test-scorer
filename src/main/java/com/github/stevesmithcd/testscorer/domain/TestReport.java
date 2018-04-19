package com.github.stevesmithcd.testscorer.domain;

import java.util.*;

public class TestReport implements Iterable<Test>, Comparable<TestReport> {
    private final String name;
    private final Date timestamp;
    private final Set<Test> tests;

    public TestReport(String name, Date timestamp, List<Test> tests) {
        this.name = name;
        this.timestamp = timestamp;
        this.tests = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public Iterator<Test> iterator() {
        return tests.iterator();
    }

    @Override
    public int compareTo(TestReport o) {
        if (timestamp.equals(o.timestamp)) return name.compareTo(o.name);

        return timestamp.before(o.timestamp) ? -1 : 1;
    }
}
