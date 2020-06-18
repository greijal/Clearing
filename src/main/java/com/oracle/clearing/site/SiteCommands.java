package com.oracle.clearing.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.File;
import java.io.IOException;

@ShellComponent()
public class SiteCommands {

    @Autowired
    private Site site;

    @ShellMethod(value = "Load new site map file", key = "load",  group = "site")
    public void load(File file) {

        try {
            site.load(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
