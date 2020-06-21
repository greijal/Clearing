package com.oracle.clearing.bulldozer.action;

import com.oracle.clearing.util.Direction;

public class ChangeDirection extends Action {

    private final Direction direction;

    public ChangeDirection(Direction direction) {
        super(Action.CHANGE_DIRECTION);
        this.direction = direction;
    }
}