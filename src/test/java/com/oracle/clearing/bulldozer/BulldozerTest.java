package com.oracle.clearing.bulldozer;


import com.oracle.clearing.site.Site;
import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.Direction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Bulldozer")
public class BulldozerTest {


    @Test
    @DisplayName("Do not move")
    public void advanceStop() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        ImmutablePair<Long, Long> lestPosition = bulldozer.lestPosition;

        bulldozer.advance(0, site);
        ImmutablePair<Long, Long> newPosition = bulldozer.lestPosition;

        assertEquals(lestPosition, newPosition);
    }

    @Test
    @DisplayName("Move RIGHT bulldozer")
    public void advanceRight() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.direction = Direction.RIGHT;
        ImmutablePair<Long, Long> lestPosition = bulldozer.lestPosition;


        bulldozer.advance(3, site);

        ImmutablePair<Long, Long> newPosition = bulldozer.lestPosition;

        assertTrue(lestPosition.getValue() < newPosition.getValue());

    }

    @Test
    @DisplayName("Move LEFT bulldozer")
    public void advanceLeft() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.direction = Direction.LEFT;
        ImmutablePair<Long, Long> lestPosition = bulldozer.lestPosition;

        bulldozer.advance(3, site);

        ImmutablePair<Long, Long> newPosition = bulldozer.lestPosition;

        assertTrue(lestPosition.getValue() > newPosition.getValue());
    }

    @Test
    @DisplayName("Move UP bulldozer")
    public void advanceUp() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.direction = Direction.UP;
        ImmutablePair<Long, Long> lestPosition = bulldozer.lestPosition;

        bulldozer.advance(3, site);

        ImmutablePair<Long, Long> newPosition = bulldozer.lestPosition;

        assertTrue(lestPosition.getKey() > newPosition.getKey());
    }

    @Test
    @DisplayName("Move DOWN bulldozer")
    public void advanceDown() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.direction = Direction.DOWN;
        ImmutablePair<Long, Long> lestPosition = bulldozer.lestPosition;

        bulldozer.advance(3, site);

        ImmutablePair<Long, Long> newPosition = bulldozer.lestPosition;

        assertTrue(lestPosition.getKey() < newPosition.getKey());
    }

    @Test
    @DisplayName("Move out")
    public void advanceTryOut() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.direction = Direction.DOWN;

        when(site.visit(anyLong(), anyLong())).thenThrow(new OutsideBorder());

        assertThrows(OutsideBorder.class, () -> {
            bulldozer.advance(3, site);
        });
    }

    @Test
    @DisplayName("Move out")
    public void advanceTre() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.direction = Direction.DOWN;

        when(site.visit(anyLong(), anyLong())).thenThrow(new ProtectAreaTree());

        assertThrows(ProtectAreaTree.class, () -> {
            bulldozer.advance(3, site);
        });
    }


    @Test
    @DisplayName("Turn -90 from right")
    public void turnNegative90Right() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.direction = Direction.RIGHT;
        bulldozer.turn(-90);
        assertEquals(Direction.UP, bulldozer.direction);
    }


    @Test
    @DisplayName("Turn -90 from left")
    public void turnNegative90Left() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.direction = Direction.LEFT;
        bulldozer.turn(-90);
        assertEquals(Direction.DOWN, bulldozer.direction);
    }

    @Test
    @DisplayName("Turn -90 from down")
    public void turnNegative90Down() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.direction = Direction.DOWN;
        bulldozer.turn(-90);
        assertEquals(Direction.RIGHT, bulldozer.direction);
    }

    @Test
    @DisplayName("Turn -90 from UP")
    public void turnNegative90Up() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.direction = Direction.UP;
        bulldozer.turn(-90);
        assertEquals(Direction.LEFT, bulldozer.direction);
    }

    @Test
    @DisplayName("Turn 90 from right")
    public void turn90Right() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.direction = Direction.RIGHT;
        bulldozer.turn(90);

        assertEquals(Direction.DOWN, bulldozer.direction);
    }


    @Test
    @DisplayName("Turn 90 from left")
    public void turn90Left() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.direction = Direction.LEFT;
        bulldozer.turn(90);
        assertEquals(Direction.UP, bulldozer.direction);
    }

    @Test
    @DisplayName("Turn 90 from down")
    public void turn90Down() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.direction = Direction.DOWN;
        bulldozer.turn(90);
        assertEquals(Direction.LEFT, bulldozer.direction);
    }

    @Test
    @DisplayName("Turn 90 from UP")
    public void turn90Up() {
        Bulldozer bulldozer = new Bulldozer();
        bulldozer.direction = Direction.UP;
        bulldozer.turn(90);
        assertEquals(Direction.RIGHT, bulldozer.direction);
    }

    @Test
    @DisplayName("Is Completed Work")
    public void isCompletedWork() {

        Site site = mock(Site.class);
        Bulldozer bulldozer = new Bulldozer();

        when(site.getUnVisitPoints()).thenReturn(Collections.EMPTY_LIST);
        assertTrue(bulldozer.isCompletedWork(site));
    }

    @Test
    @DisplayName("Is not Completed Work")
    public void isNotCompletedWork() {

        Site site = mock(Site.class);
        Bulldozer bulldozer = new Bulldozer();

        when(site.getUnVisitPoints()).thenReturn(Collections.singletonList('o'));
        assertFalse(bulldozer.isCompletedWork(site));
    }

    @Test
    @DisplayName("Find me")
    public void findMe() {

        Site site = mock(Site.class);
        Bulldozer bulldozer = new Bulldozer();
        String map = "MAP";

        when(site.getMyLocation(bulldozer.lestPosition)).thenReturn(map);
        bulldozer.findMe(site);

        assertNotNull(bulldozer.findMe(site));
    }


}
