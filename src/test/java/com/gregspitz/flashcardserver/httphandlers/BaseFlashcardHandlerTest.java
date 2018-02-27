package com.gregspitz.flashcardserver.httphandlers;

import com.google.gson.Gson;
import com.gregspitz.flashcardserver.data.FlashcardRepository;
import com.gregspitz.flashcardserver.model.Flashcard;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.io.OutputStream;
import java.net.InetSocketAddress;

import static org.mockito.Mockito.when;

public class BaseFlashcardHandlerTest {
    static final Flashcard FLASHCARD = new Flashcard("0", "Front", "Back");

    static final Gson gson = new Gson();

    @Mock
    OutputStream mockResponseBody;

    @Mock
    Headers mockResponseHeaders;

    @Mock
    HttpExchange mockHttpExchange;

    @Mock
    FlashcardRepository mockRepository;

    @Captor
    ArgumentCaptor<byte[]> responseCaptor = ArgumentCaptor.forClass(byte[].class);

    void setupBasicGetRequest() {
        when(mockHttpExchange.getResponseHeaders()).thenReturn(mockResponseHeaders);
        when(mockHttpExchange.getResponseBody()).thenReturn(mockResponseBody);
        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        when(mockHttpExchange.getRemoteAddress()).thenReturn(new InetSocketAddress("localhot", 8080));
    }
}
