package com.github.testscorer.parser.serenity;

import com.github.testscorer.domain.Result;
import com.github.testscorer.domain.TestReport;
import com.github.testscorer.domain.TestResult;
import com.github.testscorer.parser.TestReportParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDateTime;
import java.util.function.Function;

import static com.github.testscorer.domain.Result.toResult;
import static com.github.testscorer.domain.Result.valueOf;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public final class SerenityTestReportParser implements TestReportParser {
    private static final String SERENITY_CSV_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    @Override
    public TestReport parse(InputStream inputStream) throws IOException {
        try (Reader csvReader = createCsvReader(inputStream)) {
            Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
            return new TestReport(stream(csvRecords.spliterator(), false).
                                  map(toTest()).
                                  collect(toList()));
        }
    }

    private static Function<CSVRecord, TestResult> toTest() { return r -> new TestResult(getName(r), getDateTime(r), getResult(r)); }

    private static BufferedReader createCsvReader(InputStream inputStream) { return new BufferedReader(new InputStreamReader(inputStream)); }

    private static LocalDateTime getDateTime(CSVRecord record) { return LocalDateTime.parse(record.get("Date"), ofPattern(SERENITY_CSV_DATE_FORMAT)); }

    private static String getName(CSVRecord record) { return format("%s|%s", record.get("Story"), record.get("Title")); }

    private static Result getResult(CSVRecord record) { return toResult(record.get("Result")); }
}
