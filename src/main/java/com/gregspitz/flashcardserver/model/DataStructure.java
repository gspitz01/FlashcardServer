package com.gregspitz.flashcardserver.model;

import java.util.List;

public class DataStructure {
    private String name;

    private String type;

    private String description;

    private boolean inDesignPatterns;

    private boolean inCodeComplete;

    private boolean inPOSA2;

    private List<String> other;

    public DataStructure(String name, String type, String description,
                         boolean inDesignPatterns, boolean inCodeComplete,
                         boolean inPOSA2, List<String> other) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.inDesignPatterns = inDesignPatterns;
        this.inCodeComplete = inCodeComplete;
        this.inPOSA2 = inPOSA2;
        this.other = other;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isInDesignPatterns() {
        return inDesignPatterns;
    }

    public boolean isInCodeComplete() {
        return inCodeComplete;
    }

    public boolean isInPOSA2() {
        return inPOSA2;
    }

    public List<String> getOther() {
        return other;
    }
}
