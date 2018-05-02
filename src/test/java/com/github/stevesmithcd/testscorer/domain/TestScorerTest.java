package com.github.stevesmithcd.testscorer.domain;

import org.hamcrest.Matcher;

import java.time.Month;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.github.stevesmithcd.testscorer.domain.Result.*;
import static java.lang.String.format;
import static java.time.LocalDateTime.of;
import static java.time.Month.*;
import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TestScorerTest {
    private final TestScorer testScorer = new TestScorer();

    @org.junit.Test
    public void shouldScoreAFailedTestOnFailure() {
        assertThat(testScorer.score(aReport(aJanFailure(1))),
                   hasScores(aScore(1, 1)));
    }

    @org.junit.Test
    public void shouldNotScoreNonFailures() {
        assertThat(testScorer.score(aReport(aJanSuccess(1)),
                                    aReport(aFebSuccess(1))),
                   hasScores(aScore(1, 0)));

        assertThat(testScorer.score(aReport(aJanIgnored(1)),
                                    aReport(aFebIgnored(2))),
                   hasScores(aScore(1, 0), aScore(2, 0)));

    }

    @org.junit.Test
    public void shouldScoreFailedTestOnFailureAndRecency() {
        assertThat(testScorer.score(aReport(aJanSuccess(1), aJanFailure(2)),
                                    aReport(aFebSuccess(1), aFebFailure(2))),
                   hasScores(aScore(2, 3), aScore(1, 0)));

        assertThat(testScorer.score(aReport(aJanSuccess(1), aJanFailure(2)),
                                    aReport(aFebSuccess(1), aFebFailure(2))),
                   hasScores(aScore(2, 3), aScore(1, 0)));

        assertThat(testScorer.score(aReport(aJanSuccess(1), aJanFailure(2), aJanSuccess(3)),
                                    aReport(aFebSuccess(1), aFebFailure(2), aFebFailure(3))),
                   hasScores(aScore(2, 3), aScore(3, 2), aScore(1, 0)));

        assertThat(testScorer.score(aReport(aJanSuccess(1), aJanFailure(2), aJanSuccess(3)),
                                    aReport(aFebSuccess(1), aFebFailure(2), aFebFailure(3))),
                   hasScores(aScore(2, 3), aScore(3, 2), aScore(1, 0)));

        assertThat(testScorer.score(aReport(aJanSuccess(1), aJanFailure(2), aJanSuccess(3)),
                                    aReport(aFebSuccess(1), aFebFailure(2), aFebFailure(3)),
                                    aReport(aMarSuccess(1), aMarFailure(2), aMarSuccess(3)),
                                    aReport(aAprSuccess(1), aAprFailure(2), aAprSuccess(3)),
                                    aReport(aMaySuccess(1), aMayFailure(2), aMaySuccess(3)),
                                    aReport(aJunSuccess(1), aJunFailure(2), aJunSuccess(3)),
                                    aReport(aJulSuccess(1), aJulSuccess(2), aJulFailure(3)),
                                    aReport(aAugSuccess(1), aAugSuccess(2), aAugSuccess(3)),
                                    aReport(aSepSuccess(1), aSepSuccess(2), aSepSuccess(3)),
                                    aReport(aOctSuccess(1), aOctSuccess(2), aOctFailure(3)),
                                    aReport(aNovFailure(1), aNovSuccess(2), aNovSuccess(3)),
                                    aReport(aDecFailure(1), aDecSuccess(2), aDecFailure(3))),
                   hasScores(aScore(3, 31), aScore(1, 23), aScore(2, 21)));
    }

    private static TestResult aJanSuccess(int id) { return aTestResult(id, JANUARY, SUCCESS); }
    private static TestResult aJanFailure(int id) { return aTestResult(id, JANUARY, FAILED); }
    private static TestResult aJanIgnored(int id) { return aTestResult(id, JANUARY, IGNORED); }
    private static TestResult aFebSuccess(int id) { return aTestResult(id, FEBRUARY, SUCCESS); }
    private static TestResult aFebFailure(int id) { return aTestResult(id, FEBRUARY, FAILED); }
    private static TestResult aFebIgnored(int id) { return aTestResult(id, FEBRUARY, IGNORED); }
    private static TestResult aMarSuccess(int id) { return aTestResult(id, MARCH, SUCCESS); }
    private static TestResult aMarFailure(int id) { return aTestResult(id, MARCH, FAILED); }
    private static TestResult aAprSuccess(int id) { return aTestResult(id, APRIL, SUCCESS); }
    private static TestResult aAprFailure(int id) { return aTestResult(id, APRIL, FAILED); }
    private static TestResult aMaySuccess(int id) { return aTestResult(id, MAY, SUCCESS); }
    private static TestResult aMayFailure(int id) { return aTestResult(id, MAY, FAILED); }
    private static TestResult aJunSuccess(int id) { return aTestResult(id, JUNE, SUCCESS); }
    private static TestResult aJunFailure(int id) { return aTestResult(id, JUNE, FAILED); }
    private static TestResult aJulSuccess(int id) { return aTestResult(id, JULY, SUCCESS); }
    private static TestResult aJulFailure(int id) { return aTestResult(id, JULY, FAILED); }
    private static TestResult aAugSuccess(int id) { return aTestResult(id, AUGUST, SUCCESS); }
    private static TestResult aSepSuccess(int id) { return aTestResult(id, SEPTEMBER, SUCCESS); }
    private static TestResult aOctSuccess(int id) { return aTestResult(id, OCTOBER, SUCCESS); }
    private static TestResult aOctFailure(int id) { return aTestResult(id, OCTOBER, FAILED); }
    private static TestResult aNovSuccess(int id) { return aTestResult(id, NOVEMBER, SUCCESS); }
    private static TestResult aNovFailure(int id) { return aTestResult(id, NOVEMBER, FAILED); }
    private static TestResult aDecSuccess(int id) { return aTestResult(id, DECEMBER, SUCCESS); }
    private static TestResult aDecFailure(int id) { return aTestResult(id, DECEMBER, FAILED); }

    private static Matcher<SortedSet<ScoredTest>> hasScores(ScoredTest ... expected) { return equalTo(asSet(expected)); }

    private static ScoredTest aScore(int id, int score) {
        return new ScoredTest(toTestId(id), score);
    }

    private static TestReport aReport(TestResult ... tests) { return new TestReport(asList(tests)); }

    private static TestResult aTestResult(int id, Month month, Result result) { return new TestResult(toTestId(id), of(2018, month, 1, 0, 0), result); }

    private static String toTestId(int id) { return format("T%d", id); }

    @SafeVarargs
    private static <T> SortedSet<T> asSet(T ... elements) {
        return new TreeSet<>(asList(elements));
    }
}