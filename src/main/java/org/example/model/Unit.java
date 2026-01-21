package org.example.model;

public enum Unit {
    PCS("pcs"),
    GRAMS("g"),
    MILLILITERS("ml");

    private final String label;

    Unit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Unit fromString(String input) {
        return switch (input.toLowerCase()) {
            case "pcs" -> PCS;
            case "g" -> GRAMS;
            case "ml" -> MILLILITERS;
            default -> throw new IllegalArgumentException("Unknown unit");
        };
    }
}

