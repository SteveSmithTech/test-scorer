package com.github.testscorer.parser.cucumber;

import com.github.testscorer.domain.Result;
import com.github.testscorer.domain.TestReport;
import com.github.testscorer.parser.cucumber.CucumberJsonReportParser.CucumberResult;
import com.github.testscorer.parser.cucumber.CucumberJsonReportParser.Step;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CucumberJsonReportParserTest {

    Step stepOf(String resultAsString) {
        Step step = new Step();
        CucumberResult result = new CucumberResult();
        result.status = resultAsString;
        step.result = result;
        return step;
    }

    @Test
    public void shouldReportSuccessIfAllStepsPassed() {
        List<Step> steps = asList(
                stepOf("passed"),
                stepOf("passed"),
                stepOf("passed"),
                stepOf("passed"));
        Result result = new CucumberJsonReportParser().toTestResult(steps);
        assertThat(result, is(Result.SUCCESS));
    }

    @Test
    public void shouldReportIgnoredIfNoSteps() {
        List<Step> steps = asList();
        Result result = new CucumberJsonReportParser().toTestResult(steps);
        assertThat(result, is(Result.IGNORED));
    }

    @Test
    public void shouldReportFailureIfAnyStepFailed() {
        List<Step> steps = asList(
                stepOf("passed"),
                stepOf("ignored"),
                stepOf("something else"),
                stepOf("skipped"),
                stepOf("error"),
                stepOf("ambiguous"),
                stepOf("failed"));
        Result result = new CucumberJsonReportParser().toTestResult(steps);
        assertThat(result, is(Result.FAILURE));
    }

    @Test
    public void shouldReportIgnoredIfAnyStepAmbiguousAndNotFailed() {
        List<Step> steps = asList(
                stepOf("passed"),
                stepOf("ignored"),
                stepOf("something else"),
                stepOf("skipped"),
                stepOf("ambiguous"),
                stepOf("success"));
        Result result = new CucumberJsonReportParser().toTestResult(steps);
        assertThat(result, is(Result.ERROR));
    }

    @Test
    public void shouldReportIgnoredIfAnyStepUndefinedAndNotFailed() {
        List<Step> steps = asList(
                stepOf("passed"),
                stepOf("ignored"),
                stepOf("something else"),
                stepOf("skipped"),
                stepOf("undefined"),
                stepOf("success"));
        Result result = new CucumberJsonReportParser().toTestResult(steps);
        assertThat(result, is(Result.ERROR));
    }

    @Test
    public void shouldReportIgnoredIfAnyStepSkippedAndNotErrorOrFailed() {
        List<Step> steps = asList(
                stepOf("passed"),
                stepOf("ignored"),
                stepOf("something else"),
                stepOf("skipped"),
                stepOf("success"),
                stepOf("success"));
        Result result = new CucumberJsonReportParser().toTestResult(steps);
        assertThat(result, is(Result.IGNORED));
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionForInvalidFormat() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/blankfile");
        new CucumberJsonReportParser().parse(inputStream);
    }

    @Test
    public void shouldParseAValidFile() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/cucumber-example.html.json");
        TestReport report = new CucumberJsonReportParser().parse(inputStream);
        assertThat(report.stream().count(), is(4L));
        assertThat(report.stream().filter(result -> result.unsuccessful()).count(), is(2L));
    }

}