package com.github.stevesmithcd.testscorer.domain;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static java.util.Collections.emptySortedSet;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class TestReportTest {
    private static final TestReport REPORT_1_JAN = new TestReport("R1", of(2018, JANUARY, 1, 0, 0), emptySortedSet());
    private static final TestReport REPORT_2_JAN = new TestReport("R2", of(2018, JANUARY, 2, 0, 0), emptySortedSet());
    private static final TestReport REPORT_3_JAN = new TestReport("R3", of(2018, JANUARY, 3, 0, 0), emptySortedSet());

    @org.junit.Test
    public void shouldOrderByEarliestCreationTimestampFirst() throws Exception {
        assertCompare(REPORT_1_JAN, REPORT_2_JAN, -1);
        assertCompare(REPORT_2_JAN, REPORT_2_JAN, 0);
        assertCompare(REPORT_3_JAN, REPORT_2_JAN, 1);
    }

    private static void assertCompare(TestReport testReport, TestReport otherTestReport, int expected) {
        assertThat(testReport.compareTo(otherTestReport), is(expected));
    }
}
