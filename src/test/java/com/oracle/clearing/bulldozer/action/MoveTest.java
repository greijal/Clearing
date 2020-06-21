package com.oracle.clearing.bulldozer.action;


import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;



public class MoveTest {

    @Test
    public void createMoveZero() {

        Move action = new Move(new LinkedList<>());
        assertEquals(0, action.getFuel());
        assertEquals(0, action.getDamage());
    }

    @Test(expected = NullPointerException.class)
    public void createMoveNull() {
        new Move(null);
    }

    @Test
    public void createMove() {

        List<Character> lends = new LinkedList<>();
        lends.add('#');
        lends.add('o');
        lends.add('o');
        lends.add('o');

        Move action = new Move(lends);
        assertEquals(4, action.getFuel());

    }

    @Test
    public void createMoveDamage() {

        List<Character> lends = new LinkedList<>();
        lends.add('o');
        lends.add('t');
        lends.add('t');
        lends.add('t');

        Move action = new Move(lends);
        assertEquals(2, action.getDamage());
    }

    @Test
    public void createMoveKnow() {

        List<Character> lends = new LinkedList<>();
        lends.add('@');
        lends.add('*');
        lends.add('%');
        lends.add('&');

        Move action = new Move(lends);
        assertEquals(0, action.getFuel());
        assertEquals(0, action.getDamage());

    }

}
