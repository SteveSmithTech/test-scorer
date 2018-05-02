package com.github.stevesmithcd.testscorer.formatter;

import com.github.stevesmithcd.testscorer.domain.ScoredTest;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.write;
import static java.time.LocalDateTime.now;

public final class TextScoredTestsFormatter implements ScoredTestsFormatter {

    @Override
    public void format(SortedSet<ScoredTest> scoredTests, File file) throws IOException {
        write(file.toPath(), createScoreboard(scoredTests).getBytes(UTF_8));
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
        return String.format("Test Scorer : Test Scoreboard %s\n===========================\n", now().toString());
    }

    private static String createScoreboardRow(int position, ScoredTest scoredTest) {
        return String.format("%d: %s [%d]\n", position, scoredTest.getName(), scoredTest.getScore());
    }
}
