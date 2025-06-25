package com.example.price_comparison.enums;

public enum Category {
    FOOD_DRINK("食料・飲料"),
    DAILY("日用品（衛生含む）");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
