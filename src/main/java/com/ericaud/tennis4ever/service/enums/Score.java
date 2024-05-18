package com.ericaud.tennis4ever.service.enums;

public enum Score {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40);

    private final int value;

    Score(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
