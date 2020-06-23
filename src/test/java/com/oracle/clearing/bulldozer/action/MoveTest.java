package com.oracle.clearing.bulldozer.action;


import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class MoveTest {

    @Test
    public void createMoveZero() {

        Move action = Move.newAction(new LinkedList<>());
        assertEquals(0, action.getFuel());
        assertEquals(0, action.getDamage());
    }

    @Test(expected = NullPointerException.class)
    public void createMoveNull() {
        Move.newAction(null);
    }

    @Test
    public void createMove() {

        List<Character> lends = new LinkedList<>();
        lends.add('#');
        lends.add('o');
        lends.add('o');
        lends.add('o');

        Move action = Move.newAction(lends);
        assertEquals(4, action.getFuel());

    }

    @Test
    public void createMoveDamage() {

        List<Character> lends = new LinkedList<>();
        lends.add('o');
        lends.add('t');
        lends.add('t');
        lends.add('t');

        Move action = Move.newAction(lends);
        assertEquals(2, action.getDamage());
    }

    @Test
    public void createMoveKnow() {

        List<Character> lends = new LinkedList<>();
        lends.add('@');
        lends.add('*');
        lends.add('%');
        lends.add('&');

        Move action = Move.newAction(lends);
        assertEquals(0, action.getFuel());
        assertEquals(0, action.getDamage());

    }

    @Test
    public void toStringTest() {
        List<Character> lends = new LinkedList<>();
        lends.add('o');
        lends.add('t');
        lends.add('t');
        lends.add('t');

        Move action = Move.newAction(lends);

        assertEquals("advance 4", action.toString());

    }

}
