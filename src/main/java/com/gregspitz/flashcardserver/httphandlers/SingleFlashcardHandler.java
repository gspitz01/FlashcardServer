package com.gregspitz.flashcardserver.httphandlers;

import com.gregspitz.flashcardserver.data.FlashcardRepository;
import com.gregspitz.flashcardserver.model.Flashcard;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * A handler for individual flashcards at the path /flashcard/${id}
 */
public class SingleFlashcardHandler extends JsonBaseHttpHandler<Flashcard> {

    private FlashcardRepository repository;

    public SingleFlashcardHandler(FlashcardRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Flashcard createResponse(HttpExchange httpExchange) {
        String requestMethod = httpExchange.getRequestMethod();
        if (requestMethod.equals("POST")) {
            return handlePostRequest(httpExchange);
        } else {
            String flashcardId = parseURI(httpExchange.getRequestURI());
            return repository.getFlashcardById(flashcardId);
        }
    }

    private Flashcard handlePostRequest(HttpExchange httpExchange) {
        InputStream requestBody = httpExchange.getRequestBody();
        try {
            String requestJson = IOUtils.toString(requestBody, getCharset());
            Flashcard flashcard = getGson().fromJson(requestJson, Flashcard.class);
            repository.addFlashcard(flashcard);
            return flashcard;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String parseURI(URI requestURI) {
        String[] parts = requestURI.getPath().split("/");
        if (parts.length < 3) {
            return null;
        }
        return parts[2];
    }
}
