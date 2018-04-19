package com.github.stevesmithcd.testscorer.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class TestReportTest {
    private static final String YESTERDAY = "20180101T0900";
    private static final String TODAY = "20180102T0900";
    private static final String TOMORROW = "20180103T0900";

    @org.junit.Test
    public void shouldBeOrderedByEarlierTimestamp() throws Exception {
        assertThat(aTestReportFrom(YESTERDAY).compareTo(aTestReportFrom(TODAY)), is(-1));
        assertThat(aTestReportFrom(TODAY).compareTo(aTestReportFrom(TODAY)), is(0));
        assertThat(aTestReportFrom(TOMORROW).compareTo(aTestReportFrom(TODAY)), is(1));
    }

    private TestReport aTestReportFrom(String timestamp) throws ParseException {
        return new TestReport("name", new SimpleDateFormat("yyyyMMdd'T'hhMM").parse(timestamp), emptyList());
    }
}
