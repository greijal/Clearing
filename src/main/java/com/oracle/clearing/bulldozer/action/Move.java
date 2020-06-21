package com.oracle.clearing.bulldozer.action;


import java.util.List;
import java.util.stream.IntStream;

public class Move extends Action {

    private final List<Character> lends;
    private final int fuel;
    private final int damage;


    public Move(List<Character> lends) {
        super(Action.MOVE);

        this.fuel = calculateFuel(lends);
        this.damage = calculateDamage(lends);
        this.lends = lends;
    }

    private static int calculateDamage(List<Character> lends) {

        if (!lends.contains('t')) {
            return 0;
        }

        return (int) IntStream.range(0, lends.size())
                .filter(idx -> lends.get(idx) == 't' && !(idx == lends.size() - 1))
                .count();
    }

    private static int calculateFuel(List<Character> lends) {
        return lends.stream().mapToInt(Move::toFuel).sum();
    }

    private static int toFuel(Character character) {
        switch (character) {
            case 'o':
            case '#':
                return 1;
            case 'r':
            case 't':
                return 2;
            default:
                return 0;
        }
    }

    public int getFuel() {
        return fuel;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "advance " + this.lends.size();
    }
}