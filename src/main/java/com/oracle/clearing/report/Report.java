package com.oracle.clearing.report;

import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.bulldozer.action.Action;
import com.oracle.clearing.bulldozer.action.Move;
import com.oracle.clearing.site.Site;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class Report {

    private static final long FUEL_COST = 1;
    private static final long COMMUNICATION_COST = 1;
    private static final long DAMAGE_COST = 2;
    private static final long UNCLEARED_COST = 3;
    private static final long PROTECTED_TREE_COST = 10;


    public String createReport(Site site, Bulldozer bulldozer, boolean penalty) {

        StringBuilder report = new StringBuilder();
        report.append(createHistoryReport(bulldozer));
        report.append("\n\n");
        report.append(createCostReport(site, bulldozer, penalty));

        return report.toString();
    }


    private String createCostReport(Site site, Bulldozer bulldozer, boolean penalty) {

        String[][] data = new String[7][];

        data[0] = new String[]{"Item", "Quantity", "Cost"};
        data[1] = createRowCommunication(bulldozer);
        data[2] = createRowFuel(bulldozer);
        data[3] = createRowUncleared(site);
        data[4] = createRowProtectedTree(penalty);
        data[5] = createRowDamage(bulldozer);
        data[6] = createTotalRow(data);

        TableModel model = new ArrayTableModel(data);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);

        return tableBuilder.build().render(128);

    }

    private String createHistoryReport(Bulldozer bulldozer) {

        String actions = bulldozer.getActionsList()
                .stream()
                .map(action -> action.toString())
                .collect(Collectors.joining(", "));


        String[][] data = new String[2][];

        data[0] = new String[]{"These are the commands you issued:"};
        data[1] = new String[]{actions};

        TableModel model = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.air);

        return tableBuilder.build().render(128);
    }

    private String[] createTotalRow(String[][] dataTable) {
        String name = "Total";
        long value = IntStream.range(1, dataTable.length - 1)
                .mapToLong(idx -> Long.parseLong((dataTable[idx][2])))
                .sum();
        return new String[]{name, "", String.valueOf(value)};
    }


    private String[] createRowDamage(Bulldozer bulldozer) {
        String name = "paint damage to bulldozer";
        long quantity = calculateDamageQuantity(bulldozer);
        long cost = calculateDamageCost(quantity);
        return new String[]{name, String.valueOf(quantity), String.valueOf(cost)};
    }

    protected long calculateDamageQuantity(Bulldozer bulldozer) {
        return bulldozer.getActionsList()
                .stream()
                .filter(action -> action.getActionType() == Action.MOVE)
                .mapToInt(action -> ((Move) action).getDamage())
                .sum();
    }

    protected long calculateDamageCost(long quantity) {
        return quantity * DAMAGE_COST;
    }


    private String[] createRowProtectedTree(boolean event) {

        String name = "destruction of protected tree";
        long quantity = event ? 1 : 0;
        long cost = calculateProtectedTreeCost(quantity);
        return new String[]{name, String.valueOf(quantity), String.valueOf(cost)};
    }

    private long calculateProtectedTreeCost(long quantity) {
        return quantity * PROTECTED_TREE_COST;
    }


    private String[] createRowUncleared(Site site) {
        String name = "uncleared squares";
        long quantity = calculateUnclearedSquaresQuantity(site);
        long cost = calculateUnclearedSquaresCost(quantity);
        return new String[]{name, String.valueOf(quantity), String.valueOf(cost)};
    }

    public long calculateUnclearedSquaresQuantity(Site site) {
        return site.
                getUnVisitLend().size();
    }

    protected long calculateUnclearedSquaresCost(long quantity) {
        return quantity * UNCLEARED_COST;
    }


    private String[] createRowFuel(Bulldozer bulldozer) {
        String name = "fuel usage";
        long quantity = calculateFuelQuantity(bulldozer);
        long cost = calculateFuelCost(quantity);
        return new String[]{name, String.valueOf(quantity), String.valueOf(cost)};
    }

    protected long calculateFuelQuantity(Bulldozer bulldozer) {
        return bulldozer
                .getActionsList()
                .stream()
                .filter(action -> action.getActionType() == Action.MOVE)
                .mapToInt(action -> ((Move) action).getFuel())
                .sum();
    }

    protected long calculateFuelCost(long quantity) {
        return quantity * FUEL_COST;
    }

    private String[] createRowCommunication(Bulldozer bulldozer) {
        String name = "communication overhead";
        long quantity = calculateCommunicationQuantity(bulldozer);
        long cost = calculateCommunicationCost(quantity);
        return new String[]{name, String.valueOf(quantity), String.valueOf(cost)};
    }

    protected long calculateCommunicationQuantity(Bulldozer bulldozer) {
        return bulldozer.getActionsList().size();
    }

    protected long calculateCommunicationCost(long quantity) {
        return quantity * COMMUNICATION_COST;
    }


}
