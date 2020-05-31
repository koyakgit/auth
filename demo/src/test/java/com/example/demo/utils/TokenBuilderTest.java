package com.example.demo.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;

public class TokenBuilderTest {
    @Test
    public void testBuild() {
        final String token = TokenBuilder.build();
        System.out.println(token);
        assertThat(token, notNullValue());
    }
}
