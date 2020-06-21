package com.oracle.clearing.bulldozer;

import com.oracle.clearing.bulldozer.action.Action;
import com.oracle.clearing.bulldozer.action.ChangeDirection;
import com.oracle.clearing.bulldozer.action.Move;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.Direction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static com.oracle.clearing.util.Direction.*;


@Component
public class Bulldozer {

    private Direction direction = RIGHT;
    private Position lestPosition = new Position(0L, -1L);
    private List<Action> actionsList = new ArrayList<>();

    /**
     * Walk the Bulldozer on the site
     *
     * @param numberSteps
     * @param site
     * @throws ProtectAreaTree
     * @throws OutsideBorder
     */
    public void advance(Integer numberSteps, Site site) throws ProtectAreaTree, OutsideBorder {

        if (numberSteps == 0 || site == null) {
            return;
        }

        List<Position> steps = calculateNextPosition(numberSteps);

        List<Character> lends = new ArrayList<>();


        for (Position step : steps) {
            try {
                lends.add(site.visit(step.getRow(), step.getColumn()));
            } catch (OutsideBorder e) {

                actionsList.add(new Move(lends));
                this.lestPosition = step;
                throw e;

            } catch (ProtectAreaTree e) {

                actionsList.add(new Move(lends));
                this.lestPosition = step;
                throw e;

            }
        }

        actionsList.add(new Move(lends));
        this.lestPosition = steps.get(steps.size() - 1);

    }

    /**
     * Calculate the next Bulldozer positions
     *
     * @param numberSteps
     * @return
     */
    private List<Position> calculateNextPosition(int numberSteps) {

        List<Position> steps = new ArrayList<>();
        LongStream stream = LongStream.range(1, numberSteps + 1);

        if (direction == RIGHT) {
            stream.forEach(idx -> steps
                    .add(new Position(lestPosition.getRow(), lestPosition.getColumn() + idx)));
            return steps;
        }

        if (direction == LEFT) {
            stream.forEach(idx -> steps
                    .add(new Position(lestPosition.getRow(), lestPosition.getColumn() - idx)));
            return steps;

        }

        if (direction == DOWN) {
            stream.forEach(idx -> steps
                    .add(new Position(lestPosition.getRow() + idx, lestPosition.getColumn())));
            return steps;

        }

        if (direction == UP) {
            stream.forEach(idx -> steps
                    .add(new Position(lestPosition.getRow() - idx, lestPosition.getColumn())));
            return steps;
        }

        return steps;
    }

    /**
     * Turn the Bulldozer direction
     *
     * @param degrees
     */
    public void turn(int degrees) {
        int newDegrees =this.direction.value + degrees;
        Direction newDirection = Direction.getByDegrees(newDegrees);

        actionsList.add(new ChangeDirection(newDirection));
        this.direction = newDirection;
    }


    /**
     * Checks that you still have work to do
     *
     * @param site
     * @return
     */
    public boolean isCompletedWork(Site site) {
        return site.getUnVisitLend().size() == 0;
    }

    /**
     * Find the Bulldozer position on site
     *
     * @param site
     * @return
     */
    public String findMe(Site site) {
        return site.getMyLocation(lestPosition.getRow(), lestPosition.getColumn(), this.direction);
    }

    public List<Action> getActionsList() {
        return actionsList;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Position getLestPosition() {
        return lestPosition;
    }

    public static class Position {

        private final long row;
        private final long column;

        public Position(long row, long column) {
            this.row = row;
            this.column = column;
        }

        public long getRow() {
            return row;
        }

        public long getColumn() {
            return column;
        }
    }

}
