package com.oracle.clearing.bulldozer.action;

import com.oracle.clearing.util.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChangeDirectionTest {

    @Test
    public void toStringTest() {

        ChangeDirection changeDirection = new ChangeDirection(Direction.RIGHT);
        assertEquals("turn RIGHT", changeDirection.toString());

    }
}
