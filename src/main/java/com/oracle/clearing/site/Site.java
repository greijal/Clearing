package com.oracle.clearing.site;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Site {

    private char[][] matrix;

    public void load(File file) throws IOException {

        if(!file.exists()){
            throw new FileNotFoundException(file.getPath());
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());
        matrix= new char[lines.size()][];
        lines.forEach(line -> {
            matrix[lines.indexOf(line)]= line.toCharArray();
        });


    }

    public void print(){
        for (char[] row : matrix) {
            String str = "|\t";
            for (char j : row) {
                str += j + "\t";
            }
            System.out.println(str + "|");
        }
    }

}
