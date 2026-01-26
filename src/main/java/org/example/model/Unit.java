package org.example.model;

public enum Unit {
    PCS("pcs"),
    GRAMS("g"),
    MILLILITERS("ml"),
    LITERS("l"),
    KILOGRAMS("kg");

    private final String label;

    Unit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Unit fromDb(String dbValue) {
        return fromUserInput(dbValue);
    }

    public static Unit fromUserInput(String input) {
        return switch (input.toLowerCase()) {
            case "pcs" -> PCS;
            case "g" -> GRAMS;
            case "ml" -> MILLILITERS;
            case "l" -> LITERS;
            case "kg" -> KILOGRAMS;
            default -> throw new IllegalArgumentException("Unknown unit: " + input);
        };
    }



}

