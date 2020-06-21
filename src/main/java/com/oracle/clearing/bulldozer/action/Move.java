package com.oracle.clearing.bulldozer.action;


import java.util.List;
import java.util.stream.IntStream;

public class Move extends Action {

    private final int fuel;
    private final int damage;

    public Move(List<Character> sitePoints) {
        super(Action.MOVE);
        this.fuel = calculateFuel(sitePoints);
        this.damage = calculateDamage(sitePoints);
    }

    private static int calculateDamage(List<Character> sitePoints) {

        if (!sitePoints.contains('t')) {
            return 0;
        }

        return (int) IntStream.range(0, sitePoints.size())
                .filter(idx -> sitePoints.get(idx) == 't' && !(idx == sitePoints.size() - 1))
                .count();
    }

    private static int calculateFuel(List<Character> sitePoints) {
        return sitePoints.stream().mapToInt(Move::toFuel).sum();
    }

    private static int toFuel(Character character) {
        switch (character) {
            case 'o':
            case 'x':
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
}