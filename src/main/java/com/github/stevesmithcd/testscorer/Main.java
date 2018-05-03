package com.github.stevesmithcd.testscorer;

import com.github.stevesmithcd.testscorer.domain.ScoredTest;
import com.github.stevesmithcd.testscorer.domain.TestReport;
import com.github.stevesmithcd.testscorer.domain.TestScorer;
import com.github.stevesmithcd.testscorer.formatter.ScoredTestsFormatter;
import com.github.stevesmithcd.testscorer.formatter.TextScoredTestsFormatter;
import com.github.stevesmithcd.testscorer.parser.SerenityTestReportParser;
import com.github.stevesmithcd.testscorer.parser.TestReportsParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

import static com.github.stevesmithcd.testscorer.parser.FileFilters.isSerenityResultsDirectory;
import static java.lang.String.format;

public final class Main {
    private Main() {}

    public static void main (String [] args) throws IOException {
        checkArgs(args);

        List<TestReport> testReports = createTestReportsParser().parse(toTestReportsDirectory(args));
        SortedSet<ScoredTest> scoredTests = new TestScorer().score(testReports);
        createScoredTestsFormatter().format(scoredTests, toOutputFile(args));
    }

    private static void checkArgs(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException(format("java %s <test reports directory> <output file>", Main.class.getName()));
        }
    }

    private static File toTestReportsDirectory(String[] args) {
        return new File(args [0]);
    }

    private static File toOutputFile(String[] args) {
        return new File(args [1]);
    }

    private static ScoredTestsFormatter createScoredTestsFormatter() {
        return new TextScoredTestsFormatter();
    }

    private static TestReportsParser createTestReportsParser() {
        return new TestReportsParser(new SerenityTestReportParser(), isSerenityResultsDirectory());
    }
}
