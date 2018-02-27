package com.gregspitz.flashcardserver.data;

import com.gregspitz.flashcardserver.model.Flashcard;

import java.util.List;

/**
 * A simple Flashcard data source
 * TODO: create test class and fill this in
 */
public class FlashcardRepository implements FlashcardDataSource {

    @Override
    public List<Flashcard> getFlashcards() {
        return null;
    }

    @Override
    public Flashcard getFlashcardById(String id) {
        return null;
    }

    @Override
    public void updateFlashcard(Flashcard flashcard) {

    }

    @Override
    public void deleteFlashcard(String id) {

    }

    @Override
    public void addFlashcard(Flashcard flashcard) {

    }
}
