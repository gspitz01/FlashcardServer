package com.gregspitz.flashcardserver.data;

import com.gregspitz.flashcardserver.model.Flashcard;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the implementation of {@link FlashcardRepository}
 */
public class FlashcardRepositoryTest {

    private static final Flashcard FLASHCARD = new Flashcard("0", "The front", "The back");

    private static final Flashcard SECOND_FLASHCARD = new Flashcard("1", "A different front",
            "A different back");

    private FlashcardRepository repository;

    @Before
    public void setup() {
        repository = new FlashcardRepository();
    }

    @Test
    public void canAddAndRetrieveAFlashcard() {
        repository.addFlashcard(FLASHCARD);
        assertEquals(1, repository.getFlashcards().size());
        assertEquals(FLASHCARD, repository.getFlashcards().get(0));
    }

    @Test
    public void canRetrieveFlashcardById() {
        repository.addFlashcard(FLASHCARD);
        Flashcard flashcard = repository.getFlashcardById(FLASHCARD.getId());
        assertEquals(FLASHCARD, flashcard);
    }

    @Test
    public void canAddAListOfFlashcards() {
        repository.addFlashcards(Arrays.asList(FLASHCARD, SECOND_FLASHCARD));
        assertEquals(2, repository.getFlashcards().size());
    }

    @Test
    public void canDeleteAFlashcardById() {
        repository.addFlashcards(Arrays.asList(FLASHCARD, SECOND_FLASHCARD));
        repository.deleteFlashcard(FLASHCARD.getId());
        assertEquals(1, repository.getFlashcards().size());
    }

    @Test
    public void canDeleteAFlashcardByWholeFlashcard() {
        repository.addFlashcards(Arrays.asList(FLASHCARD, SECOND_FLASHCARD));
        repository.deleteFlashcard(FLASHCARD);
        assertEquals(1, repository.getFlashcards().size());
    }
}
