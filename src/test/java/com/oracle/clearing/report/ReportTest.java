package com.oracle.clearing.account;

import com.oracle.clearing.bulldozer.Account;
import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.bulldozer.action.Action;
import com.oracle.clearing.bulldozer.action.Move;
import com.oracle.clearing.site.Site;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Account")
public class ReportTest {


    @Mock
    private Site site;
    @Mock
    private Bulldozer bulldozer;
    @InjectMocks
    private Report report;

    @Test
    @DisplayName("Calculate communication quantity ")
    public void calculateCommunicationQuantity() {

        List<Action> actionList = Collections.singletonList(Mockito.mock(Action.class));
        when(bulldozer.getActionsList()).thenReturn(actionList);
        long result = report.calculateCommunicationQuantity();
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Calculate communication quantity ")
    public void calculateCommunicationCost() {

        List<Action> actionList = Collections.singletonList(Mockito.mock(Action.class));
        when(bulldozer.getActionsList()).thenReturn(actionList);
        long result = report.calculateCommunicationCost();
        assertEquals(1, result);

    }

    @Test
    @DisplayName("Calculate Fuel quantity")
    protected void calculateFuelQuantity() {

        Move act1 = Mockito.mock(Move.class);
        Move act2 = Mockito.mock(Move.class);

        List<Move> actionList = new ArrayList();
        actionList.add(act1);
        actionList.add(act2);

        when(act1.getFuel()).thenReturn(10);
        when(act2.getFuel()).thenReturn(15);

        when(bulldozer.getMoveAction()).thenReturn(actionList);

        long result = report.calculateFuelQuantity();

        assertEquals(25, result);

    }

    @Test
    @DisplayName("Calculate Fuel quantity")
    protected void calculateFuelCost() {

        Move act1 = Mockito.mock(Move.class);

        List<Move> actionList = new ArrayList();
        actionList.add(act1);

        when(act1.getFuel()).thenReturn(10);
        when(bulldozer.getMoveAction()).thenReturn(actionList);

        long result = report.calculateFuelCost();

        assertEquals(10, result);

    }


    @Test
    @DisplayName("Calculate Damage quantity")
    protected void calculateDamageQuantity() {

        Move act1 = Mockito.mock(Move.class);
        Move act2 = Mockito.mock(Move.class);

        List<Move> actionList = new ArrayList();
        actionList.add(act1);
        actionList.add(act2);

        when(act1.getDamage()).thenReturn(10);
        when(act2.getDamage()).thenReturn(15);

        when(bulldozer.getMoveAction()).thenReturn(actionList);

        long result = report.calculateDamageQuantity();

        assertEquals(25, result);

    }

    @Test
    @DisplayName("Calculate Damage cost")
    protected void calculateDamageCost() {

        Move act1 = Mockito.mock(Move.class);

        List<Move> actionList = new ArrayList();
        actionList.add(act1);

        when(act1.getDamage()).thenReturn(10);
        when(bulldozer.getMoveAction()).thenReturn(actionList);

        long result = report.calculateDamageCost();
        assertEquals(20, result);
    }

    @Test
    @DisplayName("Calculate Uncleared Squares quantity")
    protected void calculateUnclearedSquaresQuantity() {

        List<Character> points = new ArrayList<>();
        points.add('o');
        points.add('o');

        when(site.getUnVisitPoints()).thenReturn(points);

        long result = report.calculateUnclearedSquaresQuantity();
        assertEquals(2, result);

    }


    @Test
    @DisplayName("Calculate Uncleared Squares cost")
    protected void calculateUnclearedSquaresCost() {

        List<Character> points = new ArrayList<>();
        points.add('o');
        points.add('o');
        points.add('o');
        points.add('o');

        when(site.getUnVisitPoints()).thenReturn(points);

        long result = report.calculateUnclearedSquaresCost();
        assertEquals(12, result);

    }
}
