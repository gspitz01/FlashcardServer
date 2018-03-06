package com.gregspitz.flashcardserver.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FlashcardTest {

    @Test
    public void compareEqualFlashcards_success() {
        Flashcard flashcard1 = new Flashcard("0", "Front", "Back");
        Flashcard flashcard2 = new Flashcard("0", "Front", "Back");
        assertEquals(flashcard1, flashcard2);
    }

    @Test
    public void compareDifferingFlashcards_notEqual() {
        Flashcard flashcard1 = new Flashcard("0", "Front", "Back");
        Flashcard flashcard2 = new Flashcard("0", "Front", "Backa");
        assertNotEquals(flashcard1, flashcard2);
    }

    @Test
    public void compareSameReference_isEqual() {
        Flashcard flashcard1 = new Flashcard("0", "Front", "Back");
        Flashcard flashcard2 = flashcard1;
        assertEquals(flashcard1, flashcard2);
    }

    @Test
    public void compareNonFlashcardToFlashcard_notEqual() {
        String flashcard = "This is not actually a flashcard";
        Flashcard flashcard1 = new Flashcard("0", "Front", "Back");
        assertNotEquals(flashcard, flashcard1);
    }

    @Test
    public void flashcardsCreatedWithoutId_notEqual() {
        Flashcard flashcard1 = new Flashcard("Front", "Back");
        Flashcard flashcard2 = new Flashcard("Front", "Back");
        assertNotEquals(flashcard1, flashcard2);
    }

    @Test
    public void gettersAllWork() {
        String id = "0";
        String front = "Front";
        String back = "Back";
        Flashcard flashcard = new Flashcard(id, front, back);
        assertEquals(id, flashcard.getId());
        assertEquals(front, flashcard.getFront());
        assertEquals(back, flashcard.getBack());
    }
}
