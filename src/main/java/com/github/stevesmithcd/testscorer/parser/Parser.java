package com.github.stevesmithcd.testscorer.parser;

import com.github.stevesmithcd.testscorer.domain.TestReport;

import java.io.File;
import java.util.Set;

public interface Parser {

    Set<TestReport> parse(File testReportsDirectory);
}
