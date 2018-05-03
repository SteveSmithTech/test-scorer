package com.github.stevesmithcd.testscorer.domain;

@SuppressWarnings("unused")
public enum Result {
    SUCCESS,
    FAILURE,
    ERROR,
    IGNORED;

    boolean unsuccessful() {
        return this == FAILURE || this == ERROR;
    }
}