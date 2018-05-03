package com.github.stevesmithcd.testscorer.parser;

import com.github.stevesmithcd.testscorer.domain.TestReport;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.github.stevesmithcd.testscorer.parser.FileFilters.*;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

public final class SerenityTestReportsParser implements Parser<List<TestReport>> {
    private final Parser<TestReport> serenityTestReportParser;
    private static final Logger LOGGER = getLogger(SerenityTestReportsParser.class);

    public SerenityTestReportsParser(Parser<TestReport> serenityTestReportParser) {
        this.serenityTestReportParser = serenityTestReportParser;
    }

    @Override
    public List<TestReport> parse(File directory) throws IOException {
        return stream(findSerenityResultDirectories(directory)).map(parseTestReport()).flatMap(streamOf()).collect(toList());
    }

    private Function<File, Optional<TestReport>> parseTestReport() {
        return f -> {
            try {
                return of(serenityTestReportParser.parse(f));
            } catch (IOException e) {
                LOGGER.warn(format("Could not parse %s to Serenity test report", f), e);
                return empty();
            }
        };
    }

    private static File[] findSerenityResultDirectories(File directory) { return ofNullable(directory.listFiles(isSerenityResultsDirectory())).orElse(new File[0]); }

    private static Function<Optional<TestReport>, Stream<? extends TestReport>> streamOf() { return o -> o.map(Stream::of).orElseGet(Stream::empty); }
}
