package org.terraform.biome;

public enum BiomeType {

    FLAT(true),
    BEACH(true),
    RIVER(false),
    ;

    private final boolean isDry;

    BiomeType(boolean isDry) {
        this.isDry = isDry;
    }

    public boolean isDry() {
        return isDry;
    }
}
