package com.github.testscorer.domain;

import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.hash;

public final class ScoredTest implements Comparable<ScoredTest> {
    private final String name;
    private final int score;
    private final int count;

    public ScoredTest(String name, int score, int count) {
        this.name = name;
        this.score = score;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getCount() {  return count; }

    @Override
    public int compareTo(ScoredTest o) {
        return (score < o.score) ? 1 : ((score == o.score) ? name.compareTo(o.name) : -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoredTest that = (ScoredTest) o;
        return score == that.score &&
                count == that.count &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return hash(name, score, count);
    }

    @Override
    public String toString() {
        return format("%s: %d [%d]", name, score, count);
    }
}
