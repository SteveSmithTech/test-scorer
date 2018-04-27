package com.github.stevesmithcd.testscorer.domain;

import java.util.List;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class ScoredTestTest {
    private static final ScoredTest TEST_1 = new ScoredTest("testApples", 1);
    private static final ScoredTest TEST_2 = new ScoredTest("testBananas", 2);
    private static final ScoredTest TEST_3 = new ScoredTest("testOranges", 3);

    @org.junit.Test
    public void shouldOrderByHighestScore() throws Exception {
        assertCompare(TEST_2, TEST_1, 1);
        assertCompare(TEST_2, TEST_2, 0);
        assertCompare(TEST_2, TEST_3, -1);
    }


    private static void assertCompare(ScoredTest scoredTest, ScoredTest otherScoredTest, int expected) {
        assertThat(scoredTest.compareTo(otherScoredTest), is(expected));
    }
}