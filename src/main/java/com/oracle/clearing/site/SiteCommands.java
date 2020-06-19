package com.oracle.clearing.site;

import org.apache.commons.text.StringSubstitutor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@ShellComponent()
public class SiteCommands {

    protected static final String ERROR_MESSAGE_FILE = "File not exist. Please try again";
    protected static final String ERROR_MESSAGE_FILE_LOAD = "Error when loading file. Check the file used";
    protected static final String MESSAGE_LOAD_FILE = "File load with success \n\n${map} \nSIZE: ${size}";
    private final Logger LOG = Logger.getLogger(getClass().getName());
    @Autowired
    private Site site;

    @ShellMethod(value = "Load new site map file", key = "load", group = "site")
    public String load(File file) {

        if (!file.exists()) {
            LOG.info("Unable to upload site. File does not exist FILE - " + file.getPath());
            return ERROR_MESSAGE_FILE;
        }

        try {
            site.load(file);
        } catch (IOException e) {
            LOG.info("Error when trying to upload the file", e);
            return ERROR_MESSAGE_FILE_LOAD;
        }


        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("map", site.stringMap());
        valuesMap.put("size", site.stringMapSize());

        return new StringSubstitutor(valuesMap).replace(MESSAGE_LOAD_FILE);
    }
}
