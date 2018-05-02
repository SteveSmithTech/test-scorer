package com.github.stevesmithcd.testscorer.formatter;

import com.github.stevesmithcd.testscorer.domain.ScoredTest;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.TreeSet;

import static java.io.File.createTempFile;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TextTestScoreboardTest {

    @Test
    public void shouldWriteTextTestScoreboardToFile() throws IOException {
        File file = createTempFile(getClass().getSimpleName(), "txt");
        new TextScoredTestsFormatter().format(asSet(new ScoredTest("testApples", 10), new ScoredTest("testBananas", 15)), file);

        List<String> fileLines = Files.readAllLines(file.toPath());
        assertThat(fileLines.get(0).contains("Test Scorer : Test Scoreboard"), is(true));
        assertThat(fileLines.get(2), equalTo("1: testBananas [15]"));
        assertThat(fileLines.get(3), equalTo("2: testApples [10]"));
    }

    private TreeSet<ScoredTest> asSet(ScoredTest ... scoredTests) {
        return new TreeSet<>(asList(scoredTests));
    }
}