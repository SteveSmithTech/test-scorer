package com.github.stevesmithcd.testscorer.parser;

import com.github.stevesmithcd.testscorer.domain.TestReport;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface TestReportParser {

    TestReport parse(File directory) throws IOException;
}
