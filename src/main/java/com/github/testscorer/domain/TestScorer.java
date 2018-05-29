package com.github.testscorer.domain;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;

public final class TestScorer {

    public SortedSet<ScoredTest> score(TestReport... testReports) {
        return score(asList(testReports));
    }

    public SortedSet<ScoredTest> score(List<TestReport> testReports) {
        List<TestReport> sortedTestReports = sort(testReports);

        Map<String, Score> scores = new HashMap<>();
        for (int i = 0, n = sortedTestReports.size(); i < n; i++) {
            TestReport testReport = sortedTestReports.get(i);
            for (TestResult testResult : testReport) {
                final Score runningScore = scores.getOrDefault(testResult.getName(), Score.noScore());
                scores.put(testResult.getName(), runningScore.add(calculateScore(testResult, i, n)));
            }
        }

        return scores.entrySet().stream().map(e -> new ScoredTest(e.getKey(), e.getValue().getScore(), e.getValue().getCount())).collect(toCollection(TreeSet::new));
    }

    private static int calculateScore(TestResult testResult, int recencyOfTestReport, int numberOfTestReports) {
        return (testResult.unsuccessful() ? 1 : 0) * (numberOfTestReports - recencyOfTestReport);
    }

    private static List<TestReport> sort(List<TestReport> testReports) {
        List<TestReport> sorted = new ArrayList<>(testReports);
        sorted.sort(comparing(TestReport::getRunTime).reversed());
        return sorted;
    }

    private static final class Score {
        private final int score;
        private final int count;

        private Score(int score, int count) {
            this.score = score;
            this.count = count;
        }

        public int getScore() {
            return score;
        }

        public int getCount() {
            return count;
        }

        public Score add(int score) {
            return new Score(this.score + score, this.count + 1);
        }

        public static Score noScore() {
            return new Score(0, 0 );
        }
    }
}
