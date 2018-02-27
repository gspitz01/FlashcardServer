package com.gregspitz.flashcardserver.httphandlers;

import com.google.gson.reflect.TypeToken;
import com.gregspitz.flashcardserver.model.Flashcard;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FlashcardsHandlerTest extends BaseFlashcardHandlerTest {

    private FlashcardsHandler flashcardsHandler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        flashcardsHandler = new FlashcardsHandler(mockRepository);
    }

    @Test
    public void getRequest_writesListOfFlashcardJsonToResponse() throws Exception {
        List<Flashcard> flashcards = Arrays.asList(FLASHCARD);
        when(mockRepository.getFlashcards()).thenReturn(flashcards);
        setupBasicGetRequest();

        flashcardsHandler.handle(mockHttpExchange);
        verify(mockResponseBody).write(responseCaptor.capture());
        String responseString = new String(responseCaptor.getValue());
        Type listFlashcardType = new TypeToken<List<Flashcard>>() {}.getType();
        List<Flashcard> returnedFlashcards = gson.fromJson(responseString, listFlashcardType);
        assertEquals(flashcards, returnedFlashcards);
    }
}
