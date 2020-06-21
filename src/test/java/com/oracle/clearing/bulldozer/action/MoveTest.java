package com.oracle.clearing.bulldozer.action;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Move")
public class MoveTest {

    @Test
    @DisplayName("Create move without points")
    public void createMoveZero() {

        Move action = new Move(new LinkedList<>());
        assertEquals(0, action.getFuel());
        assertEquals(0, action.getDamage());
    }

    @Test
    @DisplayName("Create move null points")
    public void createMoveNull() {
        assertThrows(NullPointerException.class, () -> new Move(null));
    }

    @Test
    @DisplayName("Create move")
    public void createMove() {

        List<Character> sitePoints = new LinkedList<>();
        sitePoints.add('x');
        sitePoints.add('o');
        sitePoints.add('o');
        sitePoints.add('o');

        Move action = new Move(sitePoints);
        assertEquals(4, action.getFuel());

    }

    @Test
    @DisplayName("Create move damage")
    public void createMoveDamage() {

        List<Character> sitePoints = new LinkedList<>();
        sitePoints.add('o');
        sitePoints.add('t');
        sitePoints.add('t');
        sitePoints.add('t');

        Move action = new Move(sitePoints);
        assertEquals(2, action.getDamage());
    }

    @Test
    @DisplayName("Create move char know")
    public void createMoveKnow() {

        List<Character> sitePoints = new LinkedList<>();
        sitePoints.add('@');
        sitePoints.add('*');
        sitePoints.add('#');
        sitePoints.add('&');

        Move action = new Move(sitePoints);
        assertEquals(0, action.getFuel());
        assertEquals(0, action.getDamage());

    }

}
