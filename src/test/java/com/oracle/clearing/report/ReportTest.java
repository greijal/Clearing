package com.oracle.clearing.report;

import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.bulldozer.action.Action;
import com.oracle.clearing.bulldozer.action.Move;
import com.oracle.clearing.site.Site;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ReportTest {

    @Test
    public void calculateProtectedTreeCost() {

        Report report = new Report();
        long result = report.calculateCommunicationCost(20);
        assertEquals(20, result);
    }

    @Test
    public void calculateCommunicationQuantity() {
        Report report = new Report();
        Bulldozer bulldozer = mock(Bulldozer.class);

        List<Action> actionList = Collections.singletonList(Mockito.mock(Action.class));
        when(bulldozer.getActionsList()).thenReturn(actionList);

        long result = report.calculateCommunicationQuantity(bulldozer);

        assertEquals(1, result);
    }

    @Test
    public void calculateCommunicationCost() {
        Report report = new Report();
        long result = report.calculateCommunicationCost(1);
        assertEquals(1, result);

    }


    @Test
    public void calculateFuelQuantity() {
        Report report = new Report();
        Bulldozer bulldozer = mock(Bulldozer.class);

        Move act1 = Mockito.mock(Move.class);
        Move act2 = Mockito.mock(Move.class);

        ArrayList actionList = new ArrayList();
        actionList.add(act1);
        actionList.add(act2);

        when(act1.getFuel()).thenReturn(10);
        when(act1.getActionType()).thenReturn(Action.MOVE);

        when(act2.getFuel()).thenReturn(15);
        when(act2.getActionType()).thenReturn(Action.MOVE);


        when(bulldozer.getActionsList()).thenReturn(actionList);

        long result = report.calculateFuelQuantity(bulldozer);

        assertEquals(25, result);

    }

    @Test
    public void calculateFuelCost() {
        Report report = new Report();
        long result = report.calculateFuelCost(10);
        assertEquals(10, result);
    }


    @Test
    public void calculateDamageQuantity() {

        Report report = new Report();
        Bulldozer bulldozer = mock(Bulldozer.class);

        Move act1 = Mockito.mock(Move.class);
        Move act2 = Mockito.mock(Move.class);

        List<Action> actionList = new ArrayList();
        actionList.add(act1);
        actionList.add(act2);

        when(act1.getDamage()).thenReturn(10);
        when(act1.getActionType()).thenReturn(Action.MOVE);

        when(act2.getDamage()).thenReturn(15);
        when(act2.getActionType()).thenReturn(Action.MOVE);

        when(bulldozer.getActionsList()).thenReturn(actionList);

        long result = report.calculateDamageQuantity(bulldozer);

        assertEquals(25, result);

    }

    @Test
    public void calculateDamageCost() {
        Report report = new Report();

        long result = report.calculateDamageCost(10);
        assertEquals(20, result);
    }

    @Test
    public void calculateUnclearedSquaresQuantity() {
        Report report = new Report();
        Site site = mock(Site.class);

        List<Character> lends = new ArrayList<>();
        lends.add('o');
        lends.add('o');

        when(site.getUnVisitLend()).thenReturn(lends);


        long result = report.calculateUnclearedSquaresQuantity(site);
        assertEquals(2, result);

    }

    @Test
    public void calculateUnclearedSquaresCost() {
        Report report = new Report();

        long result = report.calculateUnclearedSquaresCost(4);
        assertEquals(12, result);
    }

}
