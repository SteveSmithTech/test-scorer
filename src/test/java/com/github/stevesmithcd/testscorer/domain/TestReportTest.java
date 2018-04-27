package com.github.stevesmithcd.testscorer.domain;

import java.time.LocalDateTime;

import static java.time.Month.*;
import static java.util.Collections.emptySet;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class TestReportTest {
    private static final TestReport REPORT_1_JAN = new TestReport("R1", LocalDateTime.of(2018, JANUARY, 1, 0, 0), emptySet());
    private static final TestReport REPORT_2_JAN = new TestReport("R2", LocalDateTime.of(2018, JANUARY, 2, 0, 0), emptySet());
    private static final TestReport REPORT_3_JAN = new TestReport("R3", LocalDateTime.of(2018, JANUARY, 3, 0, 0), emptySet());

    @org.junit.Test
    public void shouldOrderByEarliestCreationTimestamp() throws Exception {
        assertCompare(REPORT_1_JAN, REPORT_2_JAN, -1);
        assertCompare(REPORT_2_JAN, REPORT_2_JAN, 0);
        assertCompare(REPORT_3_JAN, REPORT_2_JAN, 1);
    }

    private static void assertCompare(TestReport testReport, TestReport otherTestReport, int expected) {
        assertThat(testReport.compareTo(otherTestReport), is(expected));
    }
}
