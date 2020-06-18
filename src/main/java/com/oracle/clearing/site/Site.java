package com.oracle.clearing.site;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Site {

    private File file;
    private char[][] map;

    public void load(File file) throws IOException {

        if(!file.exists()){
            throw new FileNotFoundException(file.getPath());
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());
        map= new char[lines.size()][];
        lines.forEach(line -> {
            map[lines.indexOf(line)]= line.toCharArray();
        });
    }


}
