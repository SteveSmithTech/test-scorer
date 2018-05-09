package com.github.stevesmithcd.testscorer.formatter;

import com.github.stevesmithcd.testscorer.domain.ScoredTest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.SortedSet;

public interface ScoredTestsFormatter {

    void format(SortedSet<ScoredTest> scoredTests, OutputStream outputStream) throws IOException;
}
