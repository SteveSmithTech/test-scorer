package com.github.testscorer.parser.cucumber;

import com.github.testscorer.domain.Result;
import com.github.testscorer.domain.TestReport;
import com.github.testscorer.domain.TestResult;
import com.github.testscorer.parser.TestReportParser;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

class Pair<A, B> {
    final A first;
    final B second;

    Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
}

public class CucumberJsonReportParser implements TestReportParser {
    static class Feature {
        String name;
        List<Scenario> elements;
    }

    static class CucumberResult {
        String status;
    }

    static class Step {
        CucumberResult result;
    }

    static class Scenario {
        String name;
        List<Step> steps;
    }

    Result toTestResult(List<Step> listOfSteps) {
        if (listOfSteps.isEmpty()) {
            return Result.IGNORED;
        } else if (listOfSteps.stream().allMatch(it -> it.result.status.equals("passed"))) {
            return Result.SUCCESS;
        } else if (listOfSteps.stream().anyMatch(it -> it.result.status.equals("failed"))) {
            return Result.FAILURE;
        } else if (listOfSteps.stream().anyMatch(
                it -> it.result.status.equals("ambiguous")
                        || it.result.status.equals("undefined"))) {
            return Result.ERROR;
        } else if (listOfSteps.stream().anyMatch(it -> it.result.status.equals("skipped"))) {
            return Result.IGNORED;
        } else return Result.ERROR;
    }

    private String toTestName(Pair<Feature, Scenario> featureAndScenario) {
        return featureAndScenario.first.name + ":" + featureAndScenario.second.name;
    }

    @Override
    public TestReport parse(InputStream inputStream) throws IOException {
        LocalDateTime dummyDateToUse = LocalDateTime.now();
        Type listType = new TypeToken<ArrayList<Feature>>() {
        }.getType();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        List<TestResult> listOfTestResults = asList();
        try {
            List<Feature> results = new Gson().fromJson(inputStreamReader, listType);

            listOfTestResults = results
                    .stream()
                    .flatMap(feature -> feature.elements.stream().map(scenario -> new Pair<>(feature, scenario)))
                    .map(featureScenarioPair -> new TestResult(
                            toTestName(featureScenarioPair),
                            dummyDateToUse,
                            toTestResult(featureScenarioPair.second.steps))
                    ).collect(Collectors.toList());


        } catch (JsonParseException | NullPointerException jpx) {
            throw new IOException(jpx);
        }
        return new TestReport(listOfTestResults);
    }
}