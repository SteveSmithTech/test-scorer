package com.github.testscorer.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

final class Assert {
    private Assert() {
    }

    @SuppressWarnings("unchecked")
    static <T extends Comparable> void assertCompare(T compare, T otherCompare, int expected) {
        assertThat(compare.compareTo(otherCompare), is(expected));
    }
}