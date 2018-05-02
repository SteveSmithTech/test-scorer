package com.github.stevesmithcd.testscorer.formatter;

import com.github.stevesmithcd.testscorer.domain.ScoredTest;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;

public interface ScoredTestsFormatter {

    void format(SortedSet<ScoredTest> scoredTests, File file) throws IOException;
}
