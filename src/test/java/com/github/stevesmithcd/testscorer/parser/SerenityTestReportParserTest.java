package com.github.stevesmithcd.testscorer.parser;

import com.github.stevesmithcd.testscorer.domain.TestReport;
import com.github.stevesmithcd.testscorer.domain.TestResult;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.github.stevesmithcd.testscorer.domain.Result.*;
import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class SerenityTestReportParserTest {
    private final File directory = new File("src/test/resources");
    private final Parser<TestReport> serenityTestReportParser = new SerenityTestReportParser();

    @Test
    public void shouldParseTestReport() throws IOException {
        List<TestResult> expected = asList(new TestResult("S2", of(2018, 5, 1, 8, 1), SUCCESS),
                                           new TestResult("S1", of(2018, 5, 1, 8, 5), FAILURE));

        TestReport testReport = serenityTestReportParser.parse(directory);
        assertThat(toTestResults(testReport), equalTo(expected));
    }

    private static List<TestResult> toTestResults(TestReport testReport) {
        return stream(testReport.spliterator(), false).collect(toList());
    }
}
