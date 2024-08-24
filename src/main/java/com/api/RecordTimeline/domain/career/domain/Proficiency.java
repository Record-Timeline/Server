package com.api.RecordTimeline.domain.career.domain;

public enum Proficiency {
    HIGH(5),
    UPPER_MEDIUM(4),
    MEDIUM(3),
    LOWER_MEDIUM(2),
    LOW(1);

    private final int level;

    Proficiency(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static Proficiency fromLevel(int level) {
        switch (level) {
            case 5:
                return HIGH;
            case 4:
                return UPPER_MEDIUM;
            case 3:
                return MEDIUM;
            case 2:
                return LOWER_MEDIUM;
            case 1:
                return LOW;
            default:
                throw new IllegalArgumentException("Invalid proficiency level: " + level);
        }
    }


}

