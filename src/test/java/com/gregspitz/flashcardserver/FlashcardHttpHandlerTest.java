package com.gregspitz.flashcardserver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gregspitz.flashcardserver.data.FlashcardRepository;
import com.gregspitz.flashcardserver.model.Flashcard;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FlashcardHttpHandlerTest {

    private static final Flashcard FLASHCARD = new Flashcard("0", "Front", "Back");

    @Mock
    private OutputStream mockResponseBody;

    @Mock
    private Headers mockResponseHeaders;

    @Mock
    private HttpExchange mockHttpExchange;

    @Mock
    private FlashcardRepository mockRepository;

    @Captor
    private ArgumentCaptor<byte[]> responseCaptor = ArgumentCaptor.forClass(byte[].class);

    private FlashcardHttpHandler flashcardHttpHandler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        flashcardHttpHandler = new FlashcardHttpHandler(mockRepository);
    }

    @Test
    public void getRequest_writesListOfFlashcardJsonToResponse() throws Exception {
        List<Flashcard> flashcards = Arrays.asList(FLASHCARD);
        when(mockRepository.getFlashcards()).thenReturn(flashcards);
        when(mockHttpExchange.getResponseHeaders()).thenReturn(mockResponseHeaders);
        when(mockHttpExchange.getResponseBody()).thenReturn(mockResponseBody);
        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        when(mockHttpExchange.getRemoteAddress()).thenReturn(new InetSocketAddress("localhot", 8080));

        flashcardHttpHandler.handle(mockHttpExchange);
        verify(mockResponseBody).write(responseCaptor.capture());
        String responseString = new String(responseCaptor.getValue());
        Gson gson = new Gson();
        Type listFlashcardType = new TypeToken<List<Flashcard>>() {}.getType();
        List<Flashcard> returnedFlashcards = gson.fromJson(responseString, listFlashcardType);
        assertEquals(flashcards, returnedFlashcards);
    }
}
