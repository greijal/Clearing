package com.oracle.clearing.site;

import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.Direction;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import org.ujmp.core.Matrix;
import org.ujmp.core.Matrix2D;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class Site {

    private Matrix2D matrix;

    /**
     * Load site file
     *
     * @param file
     * @throws IOException
     */
    public void load(File file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());
        char[][] chars = new char[lines.size()][];

        lines.forEach(line -> chars[lines.indexOf(line)] = line.toCharArray());

        matrix = new ArrayDenseCharMatrix2D(chars);
    }

    /**
     * Mark lend visited
     *
     * @param row
     * @param col
     * @return
     * @throws ProtectAreaTree
     * @throws OutsideBorder
     */
    public char visit(long row, long col) throws ProtectAreaTree, OutsideBorder {

        if ((row > (matrix.getRowCount() - 1) || row < 0) || (col > (matrix.getColumnCount() - 1) || col < 0)) {
            throw new OutsideBorder();
        }

        char lend = matrix.getAsChar(row, col);

        if (isProtectAreaTree(lend)) {
            throw new ProtectAreaTree();
        }

        markAsVisited(row, col);

        return lend;
    }

    private boolean isProtectAreaTree(Character lend) {
        return 'T' == lend;
    }

    public List<Character> getUnVisitLend() {

        return StreamSupport
                .stream(matrix.allValues().spliterator(), false)
                .map(o -> (Character) o)
                .filter(character -> '#' != character)
                .filter(character -> 'T' != character)
                .collect(Collectors.toList());
    }

    private void markAsVisited(long row, long col) {
        matrix.setAsChar('#', row, col);
    }


    public String stringMap() {
        return stringMap(this.matrix);
    }

    private String stringMap(Matrix matrix) {

        String[][] data = new String[10][];
        data[0] = new String[]{""};
        data[1] = new String[]{matrix.toString()};
        data[3] = new String[]{""};
        data[2] = new String[]{"Caption"};
        data[4] = new String[]{"o - Plain land"};
        data[5] = new String[]{"r - Rocky land"};
        data[6] = new String[]{"t - Removable trees"};
        data[7] = new String[]{"T - Preserved trees"};
        data[8] = new String[]{"< | > | ^ | v - Your location"};
        data[9] = new String[]{"# - Visited"};


        TableModel model = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(model);


        return tableBuilder.build().render(1024);
    }


    public String getMyLocation(long row, long col, Direction direction) {
        Matrix matrix = this.matrix.clone();
        matrix.setAsChar(direction.getSignal(), row, col);


        return stringMap(matrix);
    }

    public boolean isEmpty() {
        return matrix == null;
    }

    public Matrix2D getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix2D matrix) {
        this.matrix = matrix;
    }
}
