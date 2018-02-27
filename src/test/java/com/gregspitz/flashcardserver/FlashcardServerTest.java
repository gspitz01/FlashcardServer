package com.gregspitz.flashcardserver;

import org.junit.Before;

/**
 * Tests for the implementation of {@link FlashcardServer}
 * TODO: fill this in. Not sure how. Maybe a fake client?
 */
public class FlashcardServerTest {

    private FlashcardServer flashcardServer;

    @Before
    public void setup() {
        flashcardServer = new FlashcardServer(8080);
    }
}
