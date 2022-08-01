package com.stanxu.enums;

public enum CommentsLevel {
    GOOD(1,"Good"),
    NORMAL(2,"Normal"),
    BAD(3,"Bad");

    public final Integer type;
    public final String value;

    CommentsLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
