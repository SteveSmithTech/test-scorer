package com.github.stevesmithcd.testscorer.domain;

import org.hamcrest.Matcher;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.github.stevesmithcd.testscorer.domain.Result.*;
import static java.lang.String.format;
import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TestScorerTest {
    private final TestScorer testScorer = new TestScorer();

    @org.junit.Test
    public void shouldScoreAFailedTestOnFailure() {
        assertThat(testScorer.score(aJanReport(aFailure(1))),
                   hasScores(aScore(1, 1)));
    }

    @org.junit.Test
    public void shouldNotScoreNonFailures() {
        assertThat(testScorer.score(aJanReport(aSuccess(1)),
                                    aFebReport(aSuccess(1))),
                   hasScores(aScore(1, 0)));

        assertThat(testScorer.score(aJanReport(anIgnored(1)),
                                    aFebReport(anIgnored(2))),
                   hasScores(aScore(1, 0), aScore(2, 0)));

    }

    @org.junit.Test
    public void shouldScoreFailedTestOnFailureAndRecency() {
        assertThat(testScorer.score(aJanReport(aSuccess(1), aFailure(2)),
                                    aFebReport(aSuccess(1), aFailure(2))),
                   hasScores(aScore(2, 3), aScore(1, 0)));

        assertThat(testScorer.score(aJanReport(aSuccess(1), aFailure(2)),
                                    aFebReport(aSuccess(1), aFailure(2))),
                   hasScores(aScore(2, 3), aScore(1, 0)));

        assertThat(testScorer.score(aJanReport(aSuccess(1), aFailure(2), aSuccess(3)),
                                    aFebReport(aSuccess(1), aFailure(2), aFailure(3))),
                   hasScores(aScore(2, 3), aScore(3, 2), aScore(1, 0)));

        assertThat(testScorer.score(aJanReport(aSuccess(1), aFailure(2), aSuccess(3)),
                                    aFebReport(aSuccess(1), aFailure(2), aFailure(3))),
                   hasScores(aScore(2, 3), aScore(3, 2), aScore(1, 0)));

        assertThat(testScorer.score(aJanReport(aSuccess(1), aFailure(2), aSuccess(3)),
                                    aFebReport(aSuccess(1), aFailure(2), aFailure(3)),
                                    aMarReport(aSuccess(1), aFailure(2), aSuccess(3)),
                                    aAprReport(aSuccess(1), aFailure(2), aSuccess(3)),
                                    aMayReport(aSuccess(1), aFailure(2), aSuccess(3)),
                                    aJunReport(aSuccess(1), aFailure(2), aSuccess(3)),
                                    aJulReport(aSuccess(1), aSuccess(2), aFailure(3)),
                                    aAugReport(aSuccess(1), aSuccess(2), aSuccess(3)),
                                    aSepReport(aSuccess(1), aSuccess(2), aSuccess(3)),
                                    aOctReport(aSuccess(1), aSuccess(2), aFailure(3)),
                                    aNovReport(aFailure(1), aSuccess(2), aSuccess(3)),
                                    aDecReport(aFailure(1), aSuccess(2), aFailure(3))),
                   hasScores(aScore(3, 31), aScore(1, 23), aScore(2, 21)));
    }

    private static Matcher<SortedSet<ScoredTest>> hasScores(ScoredTest ... expected) { return equalTo(asSet(expected)); }

    private static ScoredTest aScore(int id, int score) {
        return new ScoredTest(toTestId(id), score);
    }

    private static TestReport aJanReport(Test ... tests) { return aTestReport(of(2018, 1, 1, 0, 0), tests); }
    private static TestReport aFebReport(Test ... tests) { return aTestReport(of(2018, 2, 1, 0, 0), tests); }
    private static TestReport aMarReport(Test ... tests) { return aTestReport(of(2018, 3, 1, 0, 0), tests); }
    private static TestReport aAprReport(Test ... tests) { return aTestReport(of(2018, 4, 1, 0, 0), tests); }
    private static TestReport aMayReport(Test ... tests) { return aTestReport(of(2018, 5, 1, 0, 0), tests); }
    private static TestReport aJunReport(Test ... tests) { return aTestReport(of(2018, 6, 1, 0, 0), tests); }
    private static TestReport aJulReport(Test ... tests) { return aTestReport(of(2018, 7, 1, 0, 0), tests); }
    private static TestReport aAugReport(Test ... tests) { return aTestReport(of(2018, 8, 1, 0, 0), tests); }
    private static TestReport aSepReport(Test ... tests) { return aTestReport(of(2018, 9, 1, 0, 0), tests); }
    private static TestReport aOctReport(Test ... tests) { return aTestReport(of(2018, 10, 1, 0, 0), tests); }
    private static TestReport aNovReport(Test ... tests) { return aTestReport(of(2018, 11, 1, 0, 0), tests); }
    private static TestReport aDecReport(Test ... tests) { return aTestReport(of(2018, 12, 1, 0, 0), tests); }

    private static TestReport aTestReport(LocalDateTime dateTime, Test... tests) { return new TestReport(format("R%s", dateTime.toString()), asList(tests)); }

    private static Test aSuccess(int id) { return aTest(id, SUCCESS); }

    private static Test aFailure(int id) { return aTest(id, FAILED); }

    private static Test anIgnored(int id) { return aTest(id, IGNORED); }

    private static Test aTest(int id, Result result) {
        return new Test(toTestId(id), dateTime, result);
    }

    private static String toTestId(int id) { return format("T%d", id); }

    @SafeVarargs
    private static <T> SortedSet<T> asSet(T ... elements) {
        return new TreeSet<>(asList(elements));
    }
}