package com.gregspitz.flashcardserver.httphandlers;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class JsonBaseHttpHandlerTest {
    @Test
    public void charset_isStandardUtf8() {
        assertEquals(StandardCharsets.UTF_8, JsonBaseHttpHandler.getCharset());
    }
}
