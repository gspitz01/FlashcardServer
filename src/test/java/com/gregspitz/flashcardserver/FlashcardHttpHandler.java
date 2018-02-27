package com.gregspitz.flashcardserver;

import com.gregspitz.flashcardserver.data.FlashcardRepository;
import com.gregspitz.flashcardserver.model.Flashcard;

import java.util.List;

public class FlashcardHttpHandler extends JsonBaseHttpHandler<List<Flashcard>> {

    private FlashcardRepository repository;

    public FlashcardHttpHandler(FlashcardRepository repository) {
        this.repository = repository;
    }

    @Override
    protected List<Flashcard> createResponse() {
        return repository.getFlashcards();
    }
}
