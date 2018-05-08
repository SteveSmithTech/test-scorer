package com.github.stevesmithcd.testscorer.parser;

import com.github.stevesmithcd.testscorer.domain.TestReport;
import com.github.stevesmithcd.testscorer.domain.TestResult;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import static com.github.stevesmithcd.testscorer.domain.Result.SUCCESS;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestReportsParserTest {

    @SuppressWarnings("unchecked")
    private Parser<TestReport> testReportParser = mock(Parser.class);

    private final Parser<List<TestReport>> testReportsParser = new TestReportsParser(testReportParser, isSerenityResultsDirectory());

    @Test
    public void shouldParseTestReports() throws IOException {
        when(testReportParser.parse(any(File.class))).thenReturn(new TestReport(asList(new TestResult("A", now(), SUCCESS))));

        assertThat(testReportsParser.parse(new File("src/test/resources")).isEmpty(), is(false));
    }

    private static FileFilter isSerenityResultsDirectory() { return file -> file.getName().equals("results.csv"); }
}
