package com.projectCPD.debater.sockets;

public enum TokenState {
    SEND("send"),
    STOP("stop"),
    DEBATE("debate"),
    PENDING("pending");


    public final String label;

    TokenState(String label) {
        this.label = label;
    }
}
