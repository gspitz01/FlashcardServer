package com.gregspitz.flashcardserver.httphandlers;

import com.gregspitz.flashcardserver.model.Flashcard;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the implementation of {@link SingleFlashcardHandler}
 */
public class SingleFlashcardHandlerTest extends BaseFlashcardHandlerTest {

    private SingleFlashcardHandler singleFlashcardHandler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        singleFlashcardHandler = new SingleFlashcardHandler(mockRepository);
        when(mockRepository.getFlashcardById(FLASHCARD.getId())).thenReturn(FLASHCARD);
        setupBasicGetRequest();
    }

    @Test
    public void requestSingleFlashcard_writesThatFlashcardToResponse() throws Exception {
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/flashcard/0"));
        singleFlashcardHandler.handle(mockHttpExchange);
        Flashcard returnedFlashcard = captureResponse();
        assertEquals(FLASHCARD, returnedFlashcard);
    }

    @Test
    public void requestForNonExistentFlashcard_writesNullToResponse() throws Exception {
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/flashcard/1"));
        singleFlashcardHandler.handle(mockHttpExchange);
        Flashcard returnedFlashcard = captureResponse();
        assertNull(returnedFlashcard);
    }

    private Flashcard captureResponse() throws Exception {
        verify(mockResponseBody).write(responseCaptor.capture());
        String responseString = new String(responseCaptor.getValue());
        return gson.fromJson(responseString, Flashcard.class);
    }

}
