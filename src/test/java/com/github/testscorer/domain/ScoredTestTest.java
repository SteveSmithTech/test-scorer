package com.github.testscorer.domain;

import static com.github.testscorer.domain.Assert.assertCompare;

public final class ScoredTestTest {
    private static final ScoredTest TEST_1 = new ScoredTest("testApples", 15, 1);
    private static final ScoredTest TEST_2 = new ScoredTest("testBananas", 27, 1);
    private static final ScoredTest TEST_3 = new ScoredTest("testOranges", 3, 1);
    private static final ScoredTest TEST_4 = new ScoredTest("testPears", 3, 1);

    @org.junit.Test
    public void shouldOrderByHighestScore() {
        assertCompare(TEST_1, TEST_1, 0);
        assertCompare(TEST_1, TEST_2, 1);
        assertCompare(TEST_1, TEST_3, -1);

        assertCompare(TEST_2, TEST_1, -1);
        assertCompare(TEST_2, TEST_2, 0);
        assertCompare(TEST_2, TEST_3, -1);

        assertCompare(TEST_3, TEST_1, 1);
        assertCompare(TEST_3, TEST_2, 1);
        assertCompare(TEST_3, TEST_3, 0);
    }

    @org.junit.Test
    public void shouldOrderByNameWhenScoresEqual() {
        assertCompare(TEST_3, TEST_3, 0);
        assertCompare(TEST_3, TEST_4, -1);
    }
}