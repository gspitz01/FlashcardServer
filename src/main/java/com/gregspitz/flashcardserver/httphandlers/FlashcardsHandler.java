package com.gregspitz.flashcardserver.httphandlers;

import com.gregspitz.flashcardserver.data.FlashcardRepository;
import com.gregspitz.flashcardserver.model.Flashcard;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

/**
 * A handler for all flashcards at the path /flashcards
 */
public class FlashcardsHandler extends JsonBaseHttpHandler<List<Flashcard>> {

    private FlashcardRepository repository;

    public FlashcardsHandler(FlashcardRepository repository) {
        this.repository = repository;
    }

    @Override
    protected List<Flashcard> createResponse(HttpExchange httpExchange) {
        return repository.getFlashcards();
    }
}
