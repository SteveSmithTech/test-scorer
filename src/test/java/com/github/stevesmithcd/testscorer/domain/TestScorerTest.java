package com.github.stevesmithcd.testscorer.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import static com.github.stevesmithcd.testscorer.domain.Result.*;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TestScorerTest {
    private final TestScorer testScorer = new TestScorer();

    @org.junit.Test
    public void shouldNotScoreConsistentSuccesses() {
        SortedSet<Test> tests = asSet(aTest(1, SUCCESS), aTest(2, SUCCESS));
        SortedSet<ScoredTest> expected = asSet(aScoredTest(1, 0), aScoredTest(2, 0));

        assertThat(testScorer.score(asSet(aTestReport(1, tests), aTestReport(2, tests))), equalTo(expected));
    }

    @org.junit.Test
    public void shouldScoreConsistentFailures() {
        SortedSet<Test> tests = asSet(aTest(1, SUCCESS), aTest(2, FAILED));
        SortedSet<ScoredTest> expected = asSet(aScoredTest(2, 2), aScoredTest(1, 0));

        assertThat(testScorer.score(asSet(aTestReport(1, tests), aTestReport(2, tests))), equalTo(expected));
    }


    @org.junit.Test
    public void shouldNotScoreIgnoredTests() {
        SortedSet<Test> tests = asSet(aTest(1, SUCCESS), aTest(2, IGNORED));
        SortedSet<ScoredTest> expected = asSet(aScoredTest(1, 0), aScoredTest(2, 0));

        assertThat(testScorer.score(asSet(aTestReport(1, tests), aTestReport(2, tests))), equalTo(expected));
    }


    private static ScoredTest aScoredTest(int id, int score) {
        return new ScoredTest(format("T%d", id), score);
    }

    private static TestReport aTestReport(int id, SortedSet<Test> tests) {
        return new TestReport(format("R%d", id), now(), tests);
    }

    private static Test aTest(int id, Result result) {
        return new Test(format("T%d", id), now(), result);
    }

    @SafeVarargs
    private static <T> SortedSet<T> asSet(T ... elements) {
        return new TreeSet<>(asList(elements));
    }

//  Dave Hounslow:

}