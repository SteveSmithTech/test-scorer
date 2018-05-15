package com.github.testscorer.domain;

import org.junit.Test;

import static com.github.testscorer.domain.Assert.assertCompare;
import static com.github.testscorer.domain.Result.SUCCESS;
import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static java.util.Arrays.asList;

public final class TestReportTest {
    private static final TestReport REPORT_0900 = new TestReport(asList(new TestResult("R1", of(2018, JANUARY, 1, 9, 0), SUCCESS)));
    private static final TestReport REPORT_1000 = new TestReport(asList(new TestResult("R2", of(2018, JANUARY, 1, 10, 0), SUCCESS)));
    private static final TestReport REPORT_1100 = new TestReport(asList(new TestResult("R3", of(2018, JANUARY, 1, 11, 0), SUCCESS)));

    @Test
    public void shouldOrderByLatestRunTime() {
        assertCompare(REPORT_1000, REPORT_0900, -1);
        assertCompare(REPORT_1000, REPORT_1000, 0);
        assertCompare(REPORT_1000, REPORT_1100, 1);
    }
}