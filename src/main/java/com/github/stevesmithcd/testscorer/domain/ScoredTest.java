package com.github.stevesmithcd.testscorer.domain;

import static java.lang.String.format;

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
        return (score < o.score) ? 1 : ((score == o.score) ? name.compareTo(o.name) : -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoredTest that = (ScoredTest) o;
        return score == that.score && (name != null ? name.equals(that.name) : that.name == null);
    }

    @Override
    public int hashCode() {
        return 31 * (name != null ? name.hashCode() : 0) + score;
    }

    @Override
    public String toString() {
        return format("%s: %d", name, score);
    }
}
