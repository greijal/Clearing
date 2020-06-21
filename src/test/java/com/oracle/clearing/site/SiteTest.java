package com.oracle.clearing.site;

import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class SiteTest {


    @Test(expected = FileNotFoundException.class)
    public void loadFileNotExist() throws IOException {
        Site site = new Site();


            File file = new File("");
            site.load(file);

    }

    @Test
    public void load() throws IOException {
        Site site = new Site();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("site.txt").getFile());
        site.load(file);

        assertEquals(5, site.getMatrix().getRowCount());
        assertEquals(5, site.getMatrix().getColumnCount());

    }

    @Test(expected = OutsideBorder.class)
    public void visitOut() throws ProtectAreaTree, OutsideBorder {
        Site site = new Site();

        int row = 10;
        int col = 10;
        site.setMatrix(new ArrayDenseCharMatrix2D(1, 1));

        site.visit(row, col);
    }

    @Test(expected = ProtectAreaTree.class)
    public void visitProtectTree() throws ProtectAreaTree, OutsideBorder {
        Site site = new Site();

        char[] map = new char[1];
        map[0] = 'T';

        site.setMatrix(new ArrayDenseCharMatrix2D(map));

      site.visit(0, 0);
    }

    @Test
    public void visit() throws ProtectAreaTree, OutsideBorder {
        Site site = new Site();

        char[] map = new char[1];
        map[0] = 'o';
        site.setMatrix(new ArrayDenseCharMatrix2D(map));

        char result = site.visit(0L, 0L);

        assertEquals('o', result);
        assertEquals('x', site.getMatrix().getAsChar(0, 0));
    }

    @Test
    public void getUnvisitedPoints() {
        Site site = new Site();

        char[] map = new char[2];
        map[0] = 'o';
        map[1] = 'x';

        site.setMatrix(new ArrayDenseCharMatrix2D(map));

        List<Character> result = site.getUnVisitPoints();
        assertEquals(1, result.size());
    }

    @Test
    public void getMyLocation() {

        Site site = new Site();

        char[] map = new char[1];
        map[0] = 'o';

        site.setMatrix(new ArrayDenseCharMatrix2D(map));

        String result = site.getMyLocation(new ImmutablePair<Long, Long>(0L, 0L));
        assertEquals("                   \n" +
                "*                  \n" +
                "Caption            \n" +
                "                   \n" +
                "o - Plain land     \n" +
                "r - Rocky land     \n" +
                "t - Removable trees\n" +
                "T - Preserved trees\n" +
                "* - Your location  \n" +
                "X - Visited        \n", result);

    }

    @Test
    public void stringMap() {

        Site site = new Site();

        char[] map = new char[1];
        map[0] = 'o';

        site.setMatrix(new ArrayDenseCharMatrix2D(map));

        String result = site.stringMap();
        assertEquals("                   \n" +
                "o                  \n" +
                "Caption            \n" +
                "                   \n" +
                "o - Plain land     \n" +
                "r - Rocky land     \n" +
                "t - Removable trees\n" +
                "T - Preserved trees\n" +
                "* - Your location  \n" +
                "X - Visited        \n", result);

    }
}
