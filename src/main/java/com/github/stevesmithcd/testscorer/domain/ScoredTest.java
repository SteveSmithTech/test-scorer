package com.github.stevesmithcd.testscorer.domain;

import static java.lang.Integer.compare;

public final class ScoredTest implements Comparable<ScoredTest> {
    private final String name;
    private final int score;

    public ScoredTest(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(ScoredTest o) {
        return compare(score, o.score);
    }
}
