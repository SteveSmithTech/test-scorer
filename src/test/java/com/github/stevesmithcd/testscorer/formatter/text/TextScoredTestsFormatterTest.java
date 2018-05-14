package com.github.stevesmithcd.testscorer.formatter.text;

import com.github.stevesmithcd.testscorer.domain.ScoredTest;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TextScoredTestsFormatterTest {

    @Test
    public void shouldWriteTextTestScoreboardToFile() throws IOException {
        String string = new TextScoredTestsFormatter().format(asSet(new ScoredTest("testApples", 10), new ScoredTest("testBananas", 15)));

        List<String> lines = asList(string.split("\n"));
        assertThat(lines.get(0).contains("TestResult Scorer : TestResult Scoreboard"), is(true));
        assertThat(lines.get(2), equalTo("1: testBananas [15]"));
        assertThat(lines.get(3), equalTo("2: testApples [10]"));
    }

    private SortedSet<ScoredTest> asSet(ScoredTest ... scoredTests) {
        return new TreeSet<>(asList(scoredTests));
    }
}