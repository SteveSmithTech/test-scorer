package com.github.testscorer.domain;

import org.junit.Test;

import static com.github.testscorer.domain.Result.*;
import static org.junit.Assert.assertEquals;

public final class ResultTest {

    @Test
    public void shouldParse() throws Exception {
        assertEquals(SUCCESS, toResult("SUCCESS"));
        assertEquals(FAILURE, toResult("FAILURE"));
        assertEquals(ERROR, toResult("ERROR"));
        assertEquals(IGNORED, toResult("IGNORED"));
        assertEquals(IGNORED, toResult("PENDING"));
    }
}
