package com.rikishi.rikishi.system.matching;

public enum TreeRoundName {
    FINAL, SEMI_FINAL, QUARTER_FINAL, FIRST_FIGHT;

    public int getIndexBound() {
        return switch (this) {
            case FIRST_FIGHT -> 15;
            case QUARTER_FINAL -> 7;
            case SEMI_FINAL -> 3;
            case FINAL -> 1;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public int getIndexStart() {
        return switch (this) {
            case FIRST_FIGHT -> 7;
            case QUARTER_FINAL -> 3;
            case SEMI_FINAL -> 1;
            case FINAL -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public TreeRoundName goUp() {
        return switch (this) {
            case FIRST_FIGHT -> QUARTER_FINAL;
            case SEMI_FINAL -> FINAL;
            case FINAL -> throw new IllegalArgumentException("Finals are at the top");
            case QUARTER_FINAL -> SEMI_FINAL;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}
