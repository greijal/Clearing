package com.oracle.clearing.bulldozer;

import com.oracle.clearing.bulldozer.action.Action;
import com.oracle.clearing.bulldozer.action.ChangeDirection;
import com.oracle.clearing.bulldozer.action.Move;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.Direction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static com.oracle.clearing.util.Direction.*;


@Component
public class Bulldozer {

    protected Direction direction = RIGHT;
    protected ImmutablePair<Long, Long> lestPosition = new ImmutablePair<>(0L, 0L);
    private List<Action> actionsList = new ArrayList<>();

    public void advance(Integer number, Site site) throws ProtectAreaTree, OutsideBorder {

        if (number == 0 || site == null) {
            return;
        }

        List<ImmutablePair<Long, Long>> steps = createSteps(number);
        ImmutablePair<Long, Long> newPosition = steps.get(steps.size() - 1);
        List<Character> sitePoints = new ArrayList<>();

        for (ImmutablePair<Long, Long> step : steps) {
            sitePoints.add(site.visit(step.getKey(), step.getValue()));
        }

        actionsList.add(new Move(sitePoints));
        this.lestPosition = newPosition;
    }

    private List<ImmutablePair<Long, Long>> createSteps(int number) {

        LongStream stream = LongStream.range(1, number + 1);

        List<ImmutablePair<Long, Long>> steps = new ArrayList<>();

        if (direction == RIGHT) {
            stream.forEach(idx -> steps.add(new ImmutablePair<>(lestPosition.getKey(), lestPosition.getValue() + idx)));
            return steps;
        }

        if (direction == LEFT) {
            stream.forEach(idx -> steps.add(new ImmutablePair<>(lestPosition.getKey(), lestPosition.getValue() - idx)));
            return steps;

        }

        if (direction == DOWN) {
            stream.forEach(idx -> steps.add(new ImmutablePair<>(lestPosition.getKey()+ idx, lestPosition.getValue())));
            return steps;

        }

        if (direction == UP) {
            stream.forEach(idx -> steps.add(new ImmutablePair<>(lestPosition.getKey()- idx, lestPosition.getValue())));

            return steps;

        }

        return steps;
    }

    public void turn(int degrees) {
        Direction newDirection = Direction.getByDegrees(direction.value + degrees);
        actionsList.add(new ChangeDirection(newDirection));
        this.direction = newDirection;
    }

    public List<Action> getActionsList() {
        return actionsList;
    }

    public boolean isCompletedWork(Site site) {
        return site.getUnVisitPoints().size() == 0;
    }

    public String findMe(Site site) {
        return site.getMyLocation(lestPosition);
    }

}
