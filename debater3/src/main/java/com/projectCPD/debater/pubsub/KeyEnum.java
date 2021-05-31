package com.projectCPD.debater.pubsub;

public enum KeyEnum {
    ANIMAL("animal"),
    PLASTIC("plastic"),
    EUTHANASIA("euthanasia");

    public final String label;

    KeyEnum(String label) {
        this.label = label;
    }
}
