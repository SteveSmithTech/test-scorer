package com.github.stevesmithcd.testscorer.domain;

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

        Map<String, Integer> scores = new HashMap<>();
        for (int i = 0, n = sortedTestReports.size(); i < n; i++) {
            TestReport testReport = sortedTestReports.get(i);
            for (TestResult testResult : testReport) {
                final Integer runningScore = scores.getOrDefault(testResult.getName(), 0);
                scores.put(testResult.getName(), runningScore + scoreTest(testResult, i, n));
            }
        }

        return scores.entrySet().stream().map(e -> new ScoredTest(e.getKey(), e.getValue())).collect(toCollection(TreeSet::new));
    }

    private static int scoreTest(TestResult testResult, int recencyOfTestReport, int numberOfTestReports) {
        return (testResult.unsuccessful() ? 1 : 0) * (numberOfTestReports - recencyOfTestReport);
    }

    private static List<TestReport> sort(List<TestReport> testReports) {
        List<TestReport> sorted = new ArrayList<>(testReports);
        sorted.sort(comparing(TestReport::getCreationTimestamp).reversed());
        return sorted;
    }
}
