package org.example.model;

public class Ingredient {

    private String name;
    private double amount;
    private Unit unit;

    public Ingredient(String name, double amount, Unit unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        if (amount == 1 && unit == Unit.PCS) {
            return name;
        }
        return name + " " + amount + " " + unit.getLabel();
    }
}