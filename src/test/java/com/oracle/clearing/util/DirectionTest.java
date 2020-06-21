package com.oracle.clearing.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DirectionTest {


    @Test()
    public void getByDegrees0() {
        assertEquals(Direction.LEFT, Direction.getByDegrees(0));
    }

    @Test()
    public void getByDegrees360() {
        assertEquals(Direction.LEFT, Direction.getByDegrees(360));
    }


    @Test()
    public void getByDegrees90() {
        assertEquals(Direction.UP, Direction.getByDegrees(90));
    }

    @Test()
    public void getByDegrees180() {
        assertEquals(Direction.RIGHT, Direction.getByDegrees(180));
    }

    @Test()
    public void getByDegrees270() {
        assertEquals(Direction.DOWN, Direction.getByDegrees(270));
    }

    @Test()
    public void getByDegreesNegative90() {
        assertEquals(Direction.DOWN, Direction.getByDegrees(-90));
    }

    @Test()
    public void getByDegreesUnknown() {
        assertNull(Direction.getByDegrees(666));
    }

}

