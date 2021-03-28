package com.github._1c_syntax.mdclasses.metadata.additional;

public enum MessageDirection implements EnumWithValue {
    SEND("Send"),
    RECEIVE("Receive");

    private final String value;

    MessageDirection(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return this.value;
    }
}
