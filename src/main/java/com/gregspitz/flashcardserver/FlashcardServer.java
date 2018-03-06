package com.gregspitz.flashcardserver;

import com.gregspitz.flashcardserver.data.FlashcardRepository;
import com.gregspitz.flashcardserver.httphandlers.FlashcardsHandler;
import com.gregspitz.flashcardserver.httphandlers.SingleFlashcardHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FlashcardServer {

    // TODO: this constant suggests that this should be a singleton
    private final int SERVER_CONSTANT = 0;
    private HttpServer server;

    public FlashcardServer(int port, FlashcardRepository repository) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), SERVER_CONSTANT);
            server.createContext("/flashcard", new SingleFlashcardHandler(repository));
            server.createContext("/flashcards", new FlashcardsHandler(repository));
            server.setExecutor(null);
            server.start();
            System.out.println("Flashcard Server running...");
        } catch (IOException ex) {
            System.err.println("Could not start Flashcard Server: " + ex.getMessage());
        }
    }

    public void stop() {
        server.stop(SERVER_CONSTANT);
    }
}
