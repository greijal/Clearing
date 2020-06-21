package com.oracle.clearing.account;

import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.bulldozer.action.Action;
import com.oracle.clearing.bulldozer.action.Move;
import com.oracle.clearing.site.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.table.Table;
import org.springframework.stereotype.Component;
import org.ujmp.core.stringmatrix.calculation.Stem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Report {

    private static final long FUEL_COST = 1;
    private static final long COMMUNICATION_COST = 1;
    private static final long DAMAGE_COST = 2;
    private static final long UNCLEARED_COST = 3;


    public void report(){

    }

    protected long calculateUnclearedSquaresQuantity() {
        return site.
                getUnVisitPoints()
                .stream()
                .filter(character -> 'T' != character)
                .count();
    }

    protected long calculateUnclearedSquaresCost() {
        return calculateUnclearedSquaresQuantity() * UNCLEARED_COST;
    }


    protected long calculateDamageQuantity() {
        return this
                .getMoveAction()
                .stream()
                .mapToInt(Move::getDamage)
                .sum();
    }

    protected long calculateDamageCost() {
        return this.calculateDamageQuantity() * DAMAGE_COST;
    }


    protected long calculateFuelQuantity() {
        return this
                .getMoveAction()
                .stream()
                .mapToInt(Move::getFuel)
                .sum();
    }

    protected long calculateFuelCost() {
        return this.calculateFuelQuantity() * FUEL_COST;
    }


    protected long calculateCommunicationQuantity() {
        return this.actionsList.size();
    }

    protected long calculateCommunicationCost() {
        return calculateCommunicationQuantity() * COMMUNICATION_COST;
    }

    
    protected List<Move> getMoveAction() {
        return this.actionsList
                .stream()
                .filter(action -> Action.MOVE == action.getAcrionType())
                .map(action -> (Move) action)
                .collect(Collectors.toList());
    }
    
    public void addAction(Action action){
        actionsList.add(action);
    }

}
