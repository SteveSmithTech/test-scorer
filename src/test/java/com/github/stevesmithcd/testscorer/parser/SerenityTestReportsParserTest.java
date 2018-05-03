package com.github.stevesmithcd.testscorer.parser;

import com.github.stevesmithcd.testscorer.domain.TestReport;
import com.github.stevesmithcd.testscorer.domain.TestResult;
import org.junit.Test;

import java.io.File;
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

public class SerenityTestReportsParserTest {

    @SuppressWarnings("unchecked")
    private Parser<TestReport> serenityTestReportParser = mock(Parser.class);

    private final Parser<List<TestReport>> serenityTestReportsParser = new SerenityTestReportsParser(serenityTestReportParser);

    @Test
    public void shouldParseTestReports() throws IOException {
        when(serenityTestReportParser.parse(any(File.class))).thenReturn(new TestReport(asList(new TestResult("A", now(), SUCCESS))));

        assertThat(serenityTestReportsParser.parse(new File("src/test")).isEmpty(), is(false));
    }
}
