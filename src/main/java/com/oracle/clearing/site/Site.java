package com.oracle.clearing.site;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Site {

    private char[][] map;

    /**
     * Load map from file
     *
     * @param file
     * @throws IOException
     */
    public void load(File file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());

        map = new char[lines.size()][];

        lines.forEach(line -> {
            map[lines.indexOf(line)] = line.toCharArray();
        });

    }


    public String stringMap() {

        StringBuffer buffer = new StringBuffer();

        for (char[] row : map) {
            String str = "|\t";
            for (char j : row) {
                str += j + "\t";
            }
            buffer.append(str + "|\n");
        }

        return buffer.toString();
    }

    public String stringMapSize() {
        return "[" + map.length + "] X [" + map.length + "]";
    }
}
