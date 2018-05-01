package com.github.stevesmithcd.testscorer.domain;

public final class Test {
    private final String name;
    private final Result result;

    public Test(String name, Result result) {
        this.name = name;
        this.result = result;
    }

    String getName() {
        return name;
    }

    boolean failed() {
        return result.failed();
    }
}
