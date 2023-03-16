package com.sparta.hanghaesecuritypost.entity;

public enum LikeEnum {
    POST(0),
    REPLY(1);

    private final int index;

    LikeEnum(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
