package com.gregspitz.flashcardserver.data.db;

import com.gregspitz.flashcardserver.model.Flashcard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FlashcardDatabaseTest {

    // TODO: this is inconsistent; Sometimes can't create database connection

    private static final String DB_NAME = "testDatabase";

    private static final String CONNECTION_URL = "jdbc:derby:" + DB_NAME + ";create=true";

    private static final Flashcard FLASHCARD = new Flashcard("Front", "Back");

    private FlashcardDatabase flashcardDatabase;

    @Before
    public void setup() throws Exception {
        flashcardDatabase = new FlashcardDatabase(CONNECTION_URL);
    }

    @After
    public void breakdown() {
        flashcardDatabase.close();
        try {
            deleteDirectory(new File(DB_NAME));
            (new File("derby.log")).delete();
        } catch (Exception ex) {
            System.err.println("Could not delete database.");
        }
    }

    @Test
    public void canCreateDatabase() {
        assertTrue((new File(DB_NAME)).exists());
    }

    @Test
    public void canCreateFlashcardTable() throws Exception {
        flashcardDatabase.createTable();
    }

    @Test
    public void canInsertFlashcard() throws Exception {
        flashcardDatabase.createTable();
        flashcardDatabase.insert(FLASHCARD);
    }

    @Test
    public void canRetrieveFlashcard() throws Exception {
        flashcardDatabase.createTable();
        flashcardDatabase.insert(FLASHCARD);
        List<Flashcard> flashcards = flashcardDatabase.getFlashcards();
        assertEquals(1, flashcards.size());
        assertEquals(FLASHCARD, flashcards.get(0));
    }

    @Test
    public void canRetrieveMultipleFlashcards() throws Exception {
        flashcardDatabase.createTable();
        flashcardDatabase.insert(FLASHCARD);
        flashcardDatabase.insert(new Flashcard("Fronty", "Backer"));
        flashcardDatabase.insert(new Flashcard("Fronter", "Backy"));
        List<Flashcard> flashcards = flashcardDatabase.getFlashcards();
        assertEquals(3, flashcards.size());
    }

    @Test
    public void noFlashcardsInTable_returnsEmptyList() throws Exception {
        flashcardDatabase.createTable();
        List<Flashcard> flashcards = flashcardDatabase.getFlashcards();
        assertEquals(0, flashcards.size());
    }

    @Test
    public void canRetrieveFlashcardById() throws Exception {
        flashcardDatabase.createTable();
        flashcardDatabase.insert(FLASHCARD);
        Flashcard retrievedFlashcard = flashcardDatabase.getFlashcardById(FLASHCARD.getId());
        assertEquals(FLASHCARD, retrievedFlashcard);
    }

    @Test
    public void retrieveWrongFlashcardId_returnsNull() throws Exception {
        flashcardDatabase.createTable();
        flashcardDatabase.insert(FLASHCARD);
        Flashcard flashcard = flashcardDatabase.getFlashcardById("Wrong ID");
        assertNull(flashcard);
    }

    private boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            for (File child : dir.listFiles()) {
                if (!deleteDirectory(child)) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
