package com.github.stevesmithcd.testscorer.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.time.Duration.ZERO;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class TestTest {
    private static final String YESTERDAY = "20180101T0900";
    private static final String TODAY = "20180102T0900";
    private static final String TOMORROW = "20180103T0900";

    @org.junit.Test
    public void shouldBeOrderedByEarlierTimestamp() throws Exception {
        assertThat(aTestFrom(YESTERDAY).compareTo(aTestFrom(TODAY)), is(-1));
        assertThat(aTestFrom(TODAY).compareTo(aTestFrom(TODAY)), is(0));
        assertThat(aTestFrom(TOMORROW).compareTo(aTestFrom(TODAY)), is(1));
    }

    private Test aTestFrom(String timestamp) throws ParseException {
        return new Test("name", new SimpleDateFormat("yyyyMMdd'T'hhMM").parse(timestamp), ZERO, Result.SUCCESS);
    }
}
