package com.gregspitz.flashcardserver;

import com.gregspitz.flashcardserver.data.DataStructuresToFlashcardTransformer;
import com.gregspitz.flashcardserver.data.FlashcardRepository;

import java.io.IOException;
import java.net.URL;

public class Main {

    private static final String DATA_FILE_NAME = "DataStructures.json";

    public static void main(String... args) {
        FlashcardRepository repository = new FlashcardRepository();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL dataFileResource = classLoader.getResource(DATA_FILE_NAME);
        if (dataFileResource != null) {
            String dataStructuresFile = dataFileResource.getFile();
            try {
                repository.addFlashcards(DataStructuresToFlashcardTransformer
                        .transformFromFile(dataStructuresFile));
            } catch (IOException ex) {
                System.err.println("Could not read data structures file.");
            }
        }
        FlashcardServer server = new FlashcardServer(8080, repository);
    }
}
