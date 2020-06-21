package com.oracle.clearing.util;


public enum Direction {

    LEFT(0), UP(90), RIGHT(180), DOWN(270);

    public int value;

    Direction(int value) {
        this.value = value;
    }

    public static Direction getByDegrees(int degrees) {

        switch (degrees) {
            case 0:
            case 360:
                return LEFT;
            case -270:
            case 90:
                return UP;
            case 180:
            case -180:
                return RIGHT;
            case 270:
            case -90:
                return DOWN;
        }

        return null;
    }

    public Character getSignal() {

        switch (this.value) {
            case 0:
                return '<';
            case 90:
                return '^';
            case 180:
                return '>';
            case 270:
                return 'v';
        }
        return null;
    }
}
