package com.github.stevesmithcd.testscorer.domain;

import static com.github.stevesmithcd.testscorer.domain.Result.SUCCESS;
import static java.time.Duration.ZERO;
import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class TestTest {
    private static final Test TEST_0900 = new Test("R1", of(2018, JANUARY, 1, 9, 0), ZERO, SUCCESS);
    private static final Test TEST_1000 = new Test("R2", of(2018, JANUARY, 1, 10, 0), ZERO, SUCCESS);
    private static final Test TEST_1100 = new Test("R3", of(2018, JANUARY, 1, 11, 0), ZERO, SUCCESS);

    @org.junit.Test
    public void shouldOrderByEarliestRunTimeFirst() throws Exception {
        assertCompare(TEST_1000, TEST_0900, 1);
        assertCompare(TEST_1000, TEST_1000, 0);
        assertCompare(TEST_1000, TEST_1100, -1);
    }


    private static void assertCompare(Test test, Test otherTest, int expected) {
        assertThat(test.compareTo(otherTest), is(expected));
    }
}
