package com.github.stevesmithcd.testscorer.parser;

import com.github.stevesmithcd.testscorer.domain.Result;
import com.github.stevesmithcd.testscorer.domain.TestReport;
import com.github.stevesmithcd.testscorer.domain.TestResult;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDateTime;
import java.util.function.Function;

import static com.github.stevesmithcd.testscorer.domain.Result.valueOf;
import static com.github.stevesmithcd.testscorer.parser.FileFilters.isSerenityResultsFile;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public final class SerenityTestReportParser implements Parser<TestReport> {
    private static final String SERENITY_CSV_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    @Override
    public TestReport parse(File directory) throws IOException {
        try (Reader csvReader = createCsvReader(directory)) {
            Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
            return new TestReport(stream(csvRecords.spliterator(), false).
                                  map(toTest()).
                                  collect(toList()));
        }
    }

    private static Function<CSVRecord, TestResult> toTest() { return r -> new TestResult(getName(r), getDateTime(r), getResult(r)); }

    private static BufferedReader createCsvReader(File directory) throws IOException { return new BufferedReader(new FileReader(findCsvTestReport(directory))); }

    private static LocalDateTime getDateTime(CSVRecord record) { return LocalDateTime.parse(record.get("Date"), ofPattern(SERENITY_CSV_DATE_FORMAT)); }

    private static String getName(CSVRecord record) { return record.get("Story"); }

    private static Result getResult(CSVRecord record) { return valueOf(record.get("Result")); }

    private static File findCsvTestReport(File directory) throws FileNotFoundException {
        return stream(ofNullable(directory.listFiles(isSerenityResultsFile())).orElse(new File[0])).findFirst().
                      orElseThrow(() -> new FileNotFoundException(format("Serenity results CSV file could not be found in '%s'", directory)));
    }

}
