package com.gregspitz.flashcardserver;

import com.gregspitz.flashcardserver.data.DataStructuresToFlashcardTransformer;
import com.gregspitz.flashcardserver.data.FlashcardRepository;
import com.gregspitz.flashcardserver.httphandlers.FlashcardsHandler;
import com.gregspitz.flashcardserver.httphandlers.SingleFlashcardHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;

public class FlashcardServer {

    private static final String DATA_FILE_NAME = "DataStructures.json";

    public FlashcardServer(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            FlashcardRepository repository = new FlashcardRepository();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL dataFileResource = classLoader.getResource(DATA_FILE_NAME);
            if (dataFileResource != null) {
                String dataStructuresFile = dataFileResource.getFile();
                repository.addFlashcards(DataStructuresToFlashcardTransformer
                        .transformFromFile(dataStructuresFile));
            }
            server.createContext("/flashcard", new SingleFlashcardHandler(repository));
            server.createContext("/flashcards", new FlashcardsHandler(repository));
            server.setExecutor(null);
            server.start();
            System.out.println("Flashcard Server running...");
        } catch (IOException ex) {
            System.err.println("Could not start Flashcard Server: " + ex.getMessage());
        }
    }
}
