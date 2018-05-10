package com.github.stevesmithcd.testscorer.parser;

import com.github.stevesmithcd.testscorer.domain.TestReport;

import java.io.IOException;
import java.io.InputStream;

public interface TestReportParser {

    TestReport parse(InputStream inputStream) throws IOException;
}
