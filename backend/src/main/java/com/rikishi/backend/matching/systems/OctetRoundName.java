package com.rikishi.backend.matching.systems;

public enum OctetRoundName {
    FINAL,
    SEMI_FINAL,
    FIRST_FIGHT;

    public int getIndexBound() {
        return switch (this) {
            case FIRST_FIGHT -> 7;
            case SEMI_FINAL -> 3;
            case FINAL -> 1;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public int getIndexStart() {
        return switch (this) {
            case FIRST_FIGHT -> 3;
            case SEMI_FINAL -> 1;
            case FINAL -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public OctetRoundName goUp() {
        return switch (this) {
            case SEMI_FINAL -> FINAL;
            case FINAL -> throw new IllegalArgumentException("Finals are at the top");
            case FIRST_FIGHT -> SEMI_FINAL;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}
