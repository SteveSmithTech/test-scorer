package com.github.stevesmithcd.testscorer.domain;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toCollection;

final class TestScorer {

    SortedSet<ScoredTest> score(TestReport... testReports) {
        List<TestReport> sortedTestReports = sort(testReports);

        Map<String, Integer> scores = new HashMap<>();
        for (int i = 0, n = sortedTestReports.size(); i < n; i++) {
            TestReport testReport = sortedTestReports.get(i);
            for (Test test : testReport) {
                final Integer runningScore = scores.getOrDefault(test.getName(), 0);
                scores.put(test.getName(), runningScore + scoreTest(test, i, n));
            }
        }

        return scores.entrySet().stream().map(e -> new ScoredTest(e.getKey(), e.getValue())).collect(toCollection(TreeSet::new));
    }

    private static int scoreTest(Test test, int recencyOfTestReport, int numberOfTestReports) {
        return (test.failed() ? 1 : 0) * (numberOfTestReports - recencyOfTestReport);
    }

    private static List<TestReport> sort(TestReport ... testReports) {
        List<TestReport> sorted = asList(testReports);
        sorted.sort(Comparator.comparing(TestReport::getCreationTimestamp).reversed());
        return sorted;
    }
}
