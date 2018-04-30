package com.github.stevesmithcd.testscorer.domain;

public final class ScoredTest implements Comparable<ScoredTest> {
    private final String name;
    private final int score;

    public ScoredTest(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(ScoredTest o) {
        return (score < o.score) ? 1 : ((score == o.score) ? 0 : -1);
    }
}
