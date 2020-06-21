package com.oracle.clearing.site;

import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Site")
public class SiteTest {


    @Test()
    @DisplayName("Error load file. File not exist")
    public void loadFileNotExist() {
        Site site = new Site();

        assertThrows(FileNotFoundException.class, () -> {
            File file = new File("");
            site.load(file);
        });
    }

    @Test()
    @DisplayName("Load File")
    public void load() throws IOException {
        Site site = new Site();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("site.txt").getFile());
        site.load(file);

        assertEquals(5, site.getMatrix().getRowCount());
        assertEquals(5, site.getMatrix().getColumnCount());

    }

    @Test
    @DisplayName("Visit outside border")
    public void visitOut() {
        Site site = new Site();

        int row = 10;
        int col = 10;
        site.setMatrix(new ArrayDenseCharMatrix2D(1, 1));

        assertThrows(OutsideBorder.class, () -> site.visit(row, col));
    }

    @Test
    @DisplayName("Visit outside border")
    public void visitProtectTree() {
        Site site = new Site();

        char[] map = new char[1];
        map[0] = 'T';

        site.setMatrix(new ArrayDenseCharMatrix2D(map));

        assertThrows(ProtectAreaTree.class, () -> site.visit(0, 0));
    }

    @Test
    @DisplayName("Visit site")
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
    @DisplayName("Unvisited site")
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
    @DisplayName("Get map location")
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
    @DisplayName("Get map string")
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
