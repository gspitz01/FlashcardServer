package com.gregspitz.flashcardserver.httphandlers;

import com.gregspitz.flashcardserver.data.FlashcardRepository;
import com.gregspitz.flashcardserver.httphandlers.JsonBaseHttpHandler;
import com.gregspitz.flashcardserver.model.Flashcard;
import com.sun.net.httpserver.HttpExchange;

import java.net.URI;

public class SingleFlashcardHandler extends JsonBaseHttpHandler<Flashcard> {

    private FlashcardRepository repository;

    public SingleFlashcardHandler(FlashcardRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Flashcard createResponse(HttpExchange httpExchange) {
        String flashcardId = parseURI(httpExchange.getRequestURI());
        return repository.getFlashcardById(flashcardId);
    }

    private String parseURI(URI requestURI) {
        String[] parts = requestURI.getPath().split("/");
        if (parts.length < 3) {
            return null;
        }
        return parts[2];
    }
}
