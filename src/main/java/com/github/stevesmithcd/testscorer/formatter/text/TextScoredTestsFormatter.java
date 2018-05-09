package com.github.stevesmithcd.testscorer.formatter.text;

import com.github.stevesmithcd.testscorer.domain.ScoredTest;
import com.github.stevesmithcd.testscorer.formatter.ScoredTestsFormatter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.SortedSet;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.write;
import static java.time.LocalDateTime.now;

public final class TextScoredTestsFormatter implements ScoredTestsFormatter {

    @Override
    public void format(SortedSet<ScoredTest> scoredTests, OutputStream outputStream) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(outputStream)) {
            printWriter.println(createScoreboard(scoredTests));
        }
    }

    private String createScoreboard(SortedSet<ScoredTest> scoredTests) {
        StringBuilder stringBuilder = new StringBuilder(createScoreboardHeader());

        int position = 1;
        for (ScoredTest scoredTest : scoredTests) {
            stringBuilder = stringBuilder.append(createScoreboardRow(position, scoredTest));
            position++;
        }

        return stringBuilder.toString();
    }

    private static String createScoreboardHeader() {
        return String.format("TestResult Scorer : TestResult Scoreboard %s\n===========================\n", now().toString());
    }

    private static String createScoreboardRow(int position, ScoredTest scoredTest) {
        return String.format("%d: %s [%d]\n", position, scoredTest.getName(), scoredTest.getScore());
    }
}
