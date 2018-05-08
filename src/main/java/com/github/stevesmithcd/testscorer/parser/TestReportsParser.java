package com.github.stevesmithcd.testscorer.parser;

import com.github.stevesmithcd.testscorer.domain.TestReport;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

public final class TestReportsParser implements Parser<List<TestReport>> {
    private static final Logger LOGGER = getLogger(TestReportsParser.class);
    private final Parser<TestReport> testReportParser;
    private final FileFilter fileFilter;

    public TestReportsParser(Parser<TestReport> testReportParser, FileFilter fileFilter) {
        this.testReportParser = testReportParser;
        this.fileFilter = fileFilter;
    }

    @Override
    public List<TestReport> parse(File file) throws IOException {
        return stream(findFiles(file, fileFilter)).map(parseTestReport()).flatMap(streamOf()).collect(toList());
    }

    private Function<File, Optional<TestReport>> parseTestReport() {
        return f -> {
            try {
                return of(testReportParser.parse(f));
            } catch (IOException e) {
                LOGGER.warn(format("Could not parse %s to test report", f), e);
                return empty();
            }
        };
    }

    private static File[] findFiles(File directory, FileFilter fileFilter) { return ofNullable(directory.listFiles(fileFilter)).orElse(new File[0]); }

    private static Function<Optional<TestReport>, Stream<? extends TestReport>> streamOf() { return o -> o.map(Stream::of).orElseGet(Stream::empty); }
}
