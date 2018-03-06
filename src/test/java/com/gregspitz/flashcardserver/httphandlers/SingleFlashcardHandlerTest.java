package com.gregspitz.flashcardserver.httphandlers;

import com.gregspitz.flashcardserver.model.Flashcard;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the implementation of {@link SingleFlashcardHandler}
 */
public class SingleFlashcardHandlerTest extends BaseFlashcardHandlerTest {

    private SingleFlashcardHandler singleFlashcardHandler;

    @Captor
    private ArgumentCaptor<Flashcard> repositoryArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        singleFlashcardHandler = new SingleFlashcardHandler(mockRepository);
        when(mockRepository.getFlashcardById(FLASHCARD.getId())).thenReturn(FLASHCARD);
        setupBasicGetRequest();
    }

    @Test
    public void getSingleFlashcard_writesThatFlashcardToResponse() throws Exception {
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/flashcard/0"));
        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        singleFlashcardHandler.handle(mockHttpExchange);
        Flashcard returnedFlashcard = captureResponse();
        assertEquals(FLASHCARD, returnedFlashcard);
    }

    @Test
    public void getForNonExistentFlashcard_writesNullToResponse() throws Exception {
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/flashcard/1"));
        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        singleFlashcardHandler.handle(mockHttpExchange);
        Flashcard returnedFlashcard = captureResponse();
        assertNull(returnedFlashcard);
    }

    @Test
    public void postFlashcard_savesFlashcard() throws Exception {
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/flashcard/"));
        when(mockHttpExchange.getRequestMethod()).thenReturn("POST");

        Flashcard requestFlashcard = new Flashcard("The front", "The back");
        String requestFlashcardJson = gson.toJson(requestFlashcard);
        Charset charset = StandardCharsets.UTF_8;
        InputStream inputStream = new ByteArrayInputStream(requestFlashcardJson.getBytes(charset));
        when(mockHttpExchange.getRequestBody()).thenReturn(inputStream);

        singleFlashcardHandler.handle(mockHttpExchange);

        verify(mockRepository).addFlashcard(repositoryArgumentCaptor.capture());
        Flashcard responseFlashcard = captureResponse();

        // Wrote flashcard to repository
        assertEquals(requestFlashcard, repositoryArgumentCaptor.getValue());
        // Sent back flashcard at response
        assertEquals(requestFlashcard, responseFlashcard);
    }

    @Test
    public void postNullFlashcard_doesNotAddToRepositoryAndWritesNullToResponse() throws Exception {
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/flashcard/"));
        when(mockHttpExchange.getRequestMethod()).thenReturn("POST");

        String requestJson = "";
        Charset charset = StandardCharsets.UTF_8;
        InputStream inputStream = new ByteArrayInputStream(requestJson.getBytes(charset));
        when(mockHttpExchange.getRequestBody()).thenReturn(inputStream);

        singleFlashcardHandler.handle(mockHttpExchange);

        verify(mockRepository, never()).addFlashcard(any(Flashcard.class));
        Flashcard responseFlashcard = captureResponse();
        assertEquals(null, responseFlashcard);
    }

    private Flashcard captureResponse() throws Exception {
        verify(mockResponseBody).write(responseCaptor.capture());
        String responseString = new String(responseCaptor.getValue());
        return gson.fromJson(responseString, Flashcard.class);
    }

}
