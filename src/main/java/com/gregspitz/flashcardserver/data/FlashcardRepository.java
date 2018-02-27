package com.gregspitz.flashcardserver.data;

import com.gregspitz.flashcardserver.model.Flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple Flashcard data source
 * TODO: create test class and fill this in
 */
public class FlashcardRepository implements FlashcardDataSource {

    private List<Flashcard> flashcards;

    public FlashcardRepository() {
        flashcards = new ArrayList<>();
    }

    @Override
    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    @Override
    public Flashcard getFlashcardById(String id) {
        return flashcards.stream()
                .filter(flashcard -> flashcard.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteFlashcard(String id) {
        int index = -1;
        for (int i = 0; i < flashcards.size(); i++) {
            if (flashcards.get(i).getId().equals(id)) {
                index = i;
            }
        }
        if (index != -1) {
            flashcards.remove(index);
        }
    }

    @Override
    public void deleteFlashcard(Flashcard flashcard) {
        deleteFlashcard(flashcard.getId());
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    @Override
    public void addFlashcards(List<Flashcard> flashcards) {
        this.flashcards.addAll(flashcards);
    }
}
