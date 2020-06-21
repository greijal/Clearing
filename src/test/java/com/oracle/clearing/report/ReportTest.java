package com.oracle.clearing.report;

import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.bulldozer.action.Action;
import com.oracle.clearing.bulldozer.action.Move;
import com.oracle.clearing.site.Site;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.shell.table.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Account")
public class ReportTest {


    @Test
    @DisplayName("Create report")
    public void report() {

        Bulldozer bulldozer = mock(Bulldozer.class);
        Site site = mock(Site.class);

        Move act1 = Mockito.mock(Move.class);
        Move act2 = Mockito.mock(Move.class);

        List<Action> actionList = new ArrayList();
        actionList.add(act1);
        actionList.add(act2);

        List<Character> points = new ArrayList<>();
        points.add('o');
        points.add('o');


        when(act1.getFuel()).thenReturn(10);
        when(act1.getActionType()).thenReturn(Action.MOVE);

        when(act2.getFuel()).thenReturn(15);
        when(act2.getActionType()).thenReturn(Action.MOVE);

        when(act1.getDamage()).thenReturn(2);
        when(act1.getActionType()).thenReturn(Action.MOVE);

        when(act2.getDamage()).thenReturn(1);
        when(act2.getActionType()).thenReturn(Action.MOVE);

        when(bulldozer.getActionsList()).thenReturn(actionList);
        when(site.getUnVisitPoints()).thenReturn(points);

        Report report = new Report();
        Table result = report.report(site, bulldozer, true);

        assertEquals("Item", result.getModel().getValue(0, 0));
        assertEquals("Quantity", result.getModel().getValue(0, 1));
        assertEquals("Cost", result.getModel().getValue(0, 2));

        assertEquals("communication overhead", result.getModel().getValue(1, 0));
        assertEquals("2", result.getModel().getValue(1, 1));
        assertEquals("2", result.getModel().getValue(1, 2));

        assertEquals("fuel usage", result.getModel().getValue(2, 0));
        assertEquals("25", result.getModel().getValue(2, 1));
        assertEquals("25", result.getModel().getValue(2, 2));

        assertEquals("uncleared squares", result.getModel().getValue(3, 0));
        assertEquals("2", result.getModel().getValue(3, 1));
        assertEquals("6", result.getModel().getValue(3, 2));

        assertEquals("destruction of protected tree", result.getModel().getValue(4, 0));
        assertEquals("1", result.getModel().getValue(4, 1));
        assertEquals("10", result.getModel().getValue(4, 2));

        assertEquals("paint damage to bulldozer", result.getModel().getValue(5, 0));
        assertEquals("3", result.getModel().getValue(5, 1));
        assertEquals("6", result.getModel().getValue(5, 2));

        assertEquals("Total", result.getModel().getValue(6, 0));
        assertEquals("", result.getModel().getValue(6, 1));
        assertEquals("49", result.getModel().getValue(6, 2));
    }


    @Test
    @DisplayName("Calculate protected tree cost")
    public void calculateProtectedTreeCost() {

        Report report = new Report();
        long result = report.calculateCommunicationCost(20);
        assertEquals(20, result);
    }

    @Test
    @DisplayName("Calculate communication quantity ")
    public void calculateCommunicationQuantity() {
        Report report = new Report();
        Bulldozer bulldozer = mock(Bulldozer.class);

        List<Action> actionList = Collections.singletonList(Mockito.mock(Action.class));
        when(bulldozer.getActionsList()).thenReturn(actionList);

        long result = report.calculateCommunicationQuantity(bulldozer);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("Calculate communication quantity ")
    public void calculateCommunicationCost() {
        Report report = new Report();
        long result = report.calculateCommunicationCost(1);
        assertEquals(1, result);

    }


    @Test
    @DisplayName("Calculate Fuel quantity")
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
    @DisplayName("Calculate Fuel cost")
    public void calculateFuelCost() {
        Report report = new Report();
        long result = report.calculateFuelCost(10);
        assertEquals(10, result);
    }


    @Test
    @DisplayName("Calculate Damage quantity")
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
    @DisplayName("Calculate Damage cost")
    public void calculateDamageCost() {
        Report report = new Report();

        long result = report.calculateDamageCost(10);
        assertEquals(20, result);
    }

    @Test
    @DisplayName("Calculate Uncleared Squares quantity")
    public void calculateUnclearedSquaresQuantity() {
        Report report = new Report();
        Site site = mock(Site.class);

        List<Character> points = new ArrayList<>();
        points.add('o');
        points.add('o');

        when(site.getUnVisitPoints()).thenReturn(points);


        long result = report.calculateUnclearedSquaresQuantity(site);
        assertEquals(2, result);

    }

    @Test
    @DisplayName("Calculate Uncleared Squares cost")
    public void calculateUnclearedSquaresCost() {
        Report report = new Report();

        long result = report.calculateUnclearedSquaresCost(4);
        assertEquals(12, result);
    }

}
