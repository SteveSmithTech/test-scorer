package com.github.testscorer.formatter.text;

import com.github.testscorer.domain.ScoredTest;
import com.github.testscorer.formatter.ScoredTestsFormatter;

import java.util.SortedSet;

import static java.nio.file.Files.write;
import static java.time.LocalDateTime.now;

public final class TextScoredTestsFormatter implements ScoredTestsFormatter {

    @Override
    public String format(SortedSet<ScoredTest> scoredTests) {
        return createScoreboardHeader() + createScoreboardBody(scoredTests);
    }

    private static CharSequence createScoreboardBody(SortedSet<ScoredTest> scoredTests) {
        StringBuilder stringBuilder = new StringBuilder();
        int position = 1;
        for (ScoredTest scoredTest : scoredTests) {
            stringBuilder = stringBuilder.append(createScoreboardRow(position, scoredTest));
            position++;
        }
        return stringBuilder;
    }

    private static String createScoreboardHeader() {
        return String.format("TestResult Scorer : TestResult Scoreboard %s\n===========================\n", now().toString());
    }

    private static String createScoreboardRow(int position, ScoredTest scoredTest) {
        return String.format("%d: %s [%d]\n", position, scoredTest.getName(), scoredTest.getScore());
    }
}
