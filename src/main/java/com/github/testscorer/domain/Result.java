package com.github.testscorer.domain;

import static java.util.Arrays.stream;

@SuppressWarnings("unused")
public enum Result {
    SUCCESS,
    FAILURE,
    ERROR,
    IGNORED;

    boolean unsuccessful() {
        return this == FAILURE || this == ERROR;
    }

    public static Result toResult(String result) {
        return exists(result) ? valueOf(result) : IGNORED;
    }

    private static boolean exists(String result) {
        return stream(values()).map(Enum::toString).map(String::toLowerCase).anyMatch(p -> p.equals(result.toLowerCase()));
    }
}