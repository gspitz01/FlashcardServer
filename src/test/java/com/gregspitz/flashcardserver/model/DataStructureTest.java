package com.gregspitz.flashcardserver.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataStructureTest {

    @Test
    public void gettersAllWork() {
        String name = "name";
        String type = "type";
        String description = "description";
        boolean inDesignPatterns = true;
        boolean inCodeComplete = true;
        boolean inPOSA2 = true;
        List<String> others = Arrays.asList("Fun", "Jamboree");
        DataStructure dataStructure = new DataStructure(name, type, description, inDesignPatterns,
                inCodeComplete, inPOSA2, others);
        assertEquals(name, dataStructure.getName());
        assertEquals(type, dataStructure.getType());
        assertEquals(description, dataStructure.getDescription());
        assertEquals(inDesignPatterns, dataStructure.isInDesignPatterns());
        assertEquals(inCodeComplete, dataStructure.isInCodeComplete());
        assertEquals(inPOSA2, dataStructure.isInPOSA2());
        assertEquals(others, dataStructure.getOther());
    }
}
