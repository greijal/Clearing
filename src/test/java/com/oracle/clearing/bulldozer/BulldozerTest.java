package com.oracle.clearing.bulldozer;


import com.oracle.clearing.site.Site;
import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.Direction;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BulldozerTest {


    @Test
    public void advanceStop() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        Bulldozer.Position lestPosition = bulldozer.getLestPosition();

        bulldozer.advance(0, site);
        Bulldozer.Position newPosition = bulldozer.getLestPosition();

        assertEquals(lestPosition, newPosition);
    }

    @Test
    public void advanceRight() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.setDirection(Direction.RIGHT);
        Bulldozer.Position lestPosition = bulldozer.getLestPosition();


        bulldozer.advance(3, site);

        Bulldozer.Position newPosition = bulldozer.getLestPosition();

        assertTrue(lestPosition.getColumn() < newPosition.getColumn());

    }

    @Test
    public void advanceLeft() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.setDirection(Direction.LEFT);

        Bulldozer.Position lestPosition = bulldozer.getLestPosition();

        bulldozer.advance(3, site);

        Bulldozer.Position newPosition = bulldozer.getLestPosition();

        assertTrue(lestPosition.getColumn() > newPosition.getColumn());
    }

    @Test
    public void advanceUp() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.setDirection(Direction.UP);

        Bulldozer.Position lestPosition = bulldozer.getLestPosition();

        bulldozer.advance(3, site);

        Bulldozer.Position newPosition = bulldozer.getLestPosition();

        assertTrue(lestPosition.getRow() > newPosition.getRow());
    }

    @Test
    public void advanceDown() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.setDirection(Direction.DOWN);

        Bulldozer.Position lestPosition = bulldozer.getLestPosition();

        bulldozer.advance(3, site);

        Bulldozer.Position newPosition = bulldozer.getLestPosition();

        assertTrue(lestPosition.getRow() < newPosition.getRow());
    }

    @Test(expected = OutsideBorder.class)
    public void advanceTryOut() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.setDirection(Direction.DOWN);

        when(site.visit(anyLong(), anyLong())).thenThrow(new OutsideBorder());
        bulldozer.advance(3, site);

    }

    @Test(expected = ProtectAreaTree.class)
    public void advanceTre() throws ProtectAreaTree, OutsideBorder {
        Bulldozer bulldozer = new Bulldozer();
        Site site = mock(Site.class);

        bulldozer.setDirection(Direction.DOWN);

        when(site.visit(anyLong(), anyLong())).thenThrow(new ProtectAreaTree());
        bulldozer.advance(3, site);
    }


    @Test
    public void turnNegative90Right() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.setDirection(Direction.RIGHT);

        bulldozer.turn(-90);
        assertEquals(Direction.UP, bulldozer.getDirection());
    }


    @Test
    public void turnNegative90Left() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.setDirection(Direction.LEFT);

        bulldozer.turn(-90);
        assertEquals(Direction.DOWN, bulldozer.getDirection());
    }

    @Test
    public void turnNegative90Down() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.setDirection(Direction.DOWN);
        bulldozer.turn(-90);
        assertEquals(Direction.RIGHT, bulldozer.getDirection());
    }

    @Test
    public void turnNegative90Up() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.setDirection(Direction.UP);

        bulldozer.turn(-90);
        assertEquals(Direction.LEFT, bulldozer.getDirection());
    }

    @Test
    public void turn90Right() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.setDirection(Direction.RIGHT);
        bulldozer.turn(90);

        assertEquals(Direction.DOWN, bulldozer.getDirection());
    }


    @Test
    public void turn90Left() {
        Bulldozer bulldozer = new Bulldozer();

        bulldozer.setDirection(Direction.LEFT);

        bulldozer.turn(90);
        assertEquals(Direction.UP, bulldozer.getDirection());
    }

    @Test
    public void turn90Down() {
        Bulldozer bulldozer = new Bulldozer();


        bulldozer.setDirection(Direction.DOWN);

        bulldozer.turn(90);
        assertEquals(Direction.LEFT, bulldozer.getDirection());
    }

    @Test
    public void turn90Up() {
        Bulldozer bulldozer = new Bulldozer();
        bulldozer.setDirection(Direction.UP);

        bulldozer.turn(90);
        assertEquals(Direction.RIGHT, bulldozer.getDirection());
    }

    @Test
    public void isCompletedWork() {

        Site site = mock(Site.class);
        Bulldozer bulldozer = new Bulldozer();

        when(site.getUnVisitLend()).thenReturn(Collections.EMPTY_LIST);
        assertTrue(bulldozer.isCompletedWork(site));
    }

    @Test
    public void isNotCompletedWork() {

        Site site = mock(Site.class);
        Bulldozer bulldozer = new Bulldozer();

        when(site.getUnVisitLend()).thenReturn(Collections.singletonList('o'));
        assertFalse(bulldozer.isCompletedWork(site));
    }

    @Test
    public void findMe() {

        Site site = mock(Site.class);
        Bulldozer bulldozer = new Bulldozer();
        String map = "MAP";

        when(site.getMyLocation(0l, 0l)).thenReturn(map);
        bulldozer.findMe(site);

        assertNotNull(bulldozer.findMe(site));
    }


}
