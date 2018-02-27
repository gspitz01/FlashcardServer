package com.gregspitz.flashcardserver.data;

import com.gregspitz.flashcardserver.model.Flashcard;

import java.util.List;

/**
 * An interface for Flashcard data sources
 */
public interface FlashcardDataSource {

    List<Flashcard> getFlashcards();

    Flashcard getFlashcardById(String id);

    void updateFlashcard(Flashcard flashcard);

    void deleteFlashcard(String id);

    void addFlashcard(Flashcard flashcard);
}
