package com.github.stevesmithcd.testscorer.domain;

@SuppressWarnings("unused")
public enum Result {
    SUCCESS,
    FAILED,
    ERROR,
    IGNORED;

    boolean unsuccessful() {
        return this == FAILED || this == ERROR;
    }
}