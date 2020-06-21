package com.oracle.clearing.bulldozer.action;

public abstract class Action {

    public static final String CHANGE_DIRECTION = "CHANGE_DIRECTION";
    public static final String MOVE = "MOVE";

    private final String actionType;

    public Action(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }
}