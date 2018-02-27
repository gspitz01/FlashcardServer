package com.gregspitz.flashcardserver.data;

import com.gregspitz.flashcardserver.model.Flashcard;

import java.util.List;

/**
 * An interface for Flashcard data sources
 * Flashcards are immutable so they cannot be updated
 */
public interface FlashcardDataSource {

    List<Flashcard> getFlashcards();

    Flashcard getFlashcardById(String id);

    void deleteFlashcard(String id);

    void deleteFlashcard(Flashcard flashcard);

    void addFlashcard(Flashcard flashcard);

    void addFlashcards(List<Flashcard> flashcards);
}
