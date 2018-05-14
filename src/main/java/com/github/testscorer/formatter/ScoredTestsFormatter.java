package com.github.testscorer.formatter;

import com.github.testscorer.domain.ScoredTest;

import java.util.SortedSet;

public interface ScoredTestsFormatter {

    String format(SortedSet<ScoredTest> scoredTests);
}
