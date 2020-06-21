package com.oracle.clearing.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test direction")
public class DirectionTest {


    @Test()
    @DisplayName("Get  by degrees 0")
    public void getByDegrees0() {
        assertEquals(Direction.LEFT, Direction.getByDegrees(0));
    }

    @Test()
    @DisplayName("Get  by degrees 360")
    public void getByDegrees360() {
        assertEquals(Direction.LEFT, Direction.getByDegrees(360));
    }


    @Test()
    @DisplayName("Get  by degrees 90")
    public void getByDegrees90() {
        assertEquals(Direction.UP, Direction.getByDegrees(90));
    }

    @Test()
    @DisplayName("Get  by degrees 180")
    public void getByDegrees180() {
        assertEquals(Direction.RIGHT, Direction.getByDegrees(180));
    }

    @Test()
    @DisplayName("Get  by degrees 270")
    public void getByDegrees270() {
        assertEquals(Direction.DOWN, Direction.getByDegrees(270));
    }

    @Test()
    @DisplayName("Get  by degrees -90")
    public void getByDegreesNegative90() {
        assertEquals(Direction.DOWN, Direction.getByDegrees(-90));
    }

    @Test()
    @DisplayName("Get  by degrees unknown")
    public void getByDegreesUnknown() {
        assertNull(Direction.getByDegrees(666));
    }

}

