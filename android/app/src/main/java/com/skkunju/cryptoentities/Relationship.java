package com.skkunju.cryptoentities;

public class Relationship {
    public final String source;
    public final String target;
    public final String description;

    public Relationship(String source, String target, String description) {
        this.source = source;
        this.target = target;
        this.description = description;
    }

    public boolean touches(String entityName) {
        return same(source, entityName) || same(target, entityName);
    }

    private static boolean same(String label, String entityName) {
        if (label == null || entityName == null) {
            return false;
        }
        String left = normalize(label);
        String right = normalize(entityName);
        return left.equals(right) || left.startsWith(right) || right.startsWith(left);
    }

    private static String normalize(String value) {
        String trimmed = value.trim().toLowerCase();
        if (trimmed.endsWith("s")) {
            return trimmed.substring(0, trimmed.length() - 1);
        }
        return trimmed;
    }
}
