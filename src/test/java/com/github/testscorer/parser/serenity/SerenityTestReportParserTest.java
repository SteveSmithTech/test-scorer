package com.github.testscorer.parser.serenity;

import com.github.testscorer.domain.TestReport;
import com.github.testscorer.domain.TestResult;
import com.github.testscorer.parser.TestReportParser;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.github.testscorer.domain.Result.FAILURE;
import static com.github.testscorer.domain.Result.SUCCESS;
import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class SerenityTestReportParserTest {
    private final File file = new File("src/test/resources/results.csv");
    private final TestReportParser testReportParser = new SerenityTestReportParser();

    @Test
    public void shouldParseTestReport() throws IOException {
        List<TestResult> expected = asList(new TestResult("S2|T2", of(2018, 5, 1, 8, 1), SUCCESS),
                                           new TestResult("S1|T1", of(2018, 5, 1, 8, 5), FAILURE));

        TestReport testReport = testReportParser.parse(new FileInputStream(file));
        assertThat(toTestResults(testReport), equalTo(expected));
    }

    private static List<TestResult> toTestResults(TestReport testReport) {
        return stream(testReport.spliterator(), false).collect(toList());
    }
}
