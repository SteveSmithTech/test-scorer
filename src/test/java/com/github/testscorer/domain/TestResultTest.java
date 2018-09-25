package com.github.testscorer.domain;

import org.junit.Test;

import static com.github.testscorer.domain.Assert.assertCompare;
import static com.github.testscorer.domain.Result.SUCCESS;
import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

public final class TestResultTest {
    private static final TestResult TEST_0900 = new TestResult("R1", of(2018, JANUARY, 1, 9, 0), SUCCESS);
    private static final TestResult TEST_1000 = new TestResult("R2", of(2018, JANUARY, 1, 10, 0), SUCCESS);
    private static final TestResult TEST_1100 = new TestResult("R3", of(2018, JANUARY, 1, 11, 0), SUCCESS);

    @Test
    public void shouldOrderByEarliestRunTime() throws Exception {
        assertCompare(TEST_1000, TEST_0900, 1);
        assertCompare(TEST_1000, TEST_1000, 0);
        assertCompare(TEST_1000, TEST_1100, -1);
    }

    @Test
    public void shouldBeAbleToInterrogate(){
      assertEquals("R1", TEST_0900.getName());
      assertEquals(SUCCESS, TEST_0900.getResult());
    }
}
