package com.gregspitz.flashcardserver.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gregspitz.flashcardserver.model.DataStructure;
import com.gregspitz.flashcardserver.model.Flashcard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for transforming a json file of {@link DataStructure}s
 * into a list of {@link Flashcard}s
 */
public class DataStructuresToFlashcardTransformer {

    public static List<Flashcard> transformFromFile(String file) throws IOException {
        Gson gson = new Gson();
        List<Flashcard> flashcards;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type type = new TypeToken<List<DataStructure>>(){}.getType();
            List<DataStructure> dataStructures = gson.fromJson(reader, type);
            flashcards = transformDataStructures(dataStructures);
        }
        return flashcards;
    }

    private static List<Flashcard> transformDataStructures(List<DataStructure> dataStructures) {
        List<Flashcard> flashcards = new ArrayList<>();
        for (DataStructure dataStructure : dataStructures) {
            String front = createFront(dataStructure);
            String back = createBack(dataStructure);
            flashcards.add(new Flashcard(front, back));
        }
        return flashcards;
    }

    private static String createBack(DataStructure dataStructure) {
        String back = dataStructure.getDescription() + "\n"
                + dataStructure.getType()
                + "\nIs in Code Complete: " + dataStructure.isInCodeComplete()
                + "\nIs in Design Patterns: " + dataStructure.isInDesignPatterns()
                + "\nIs in POSA2: " + dataStructure.isInPOSA2();
        if (dataStructure.getOther() != null) {
            back += "\nOthers:";
            StringBuilder others = new StringBuilder();
            for (String other : dataStructure.getOther()) {
                others.append("\n").append(other);
            }
            back += others.toString();
        }
        return back;
    }

    private static String createFront(DataStructure dataStructure) {
        return dataStructure.getName();
    }
}
