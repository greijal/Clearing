package com.oracle.clearing.site;

import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Test Site")
public class SiteCommandsTest {

    @Mock
    private Site site;
    @InjectMocks
    private SiteCommands siteCommands;


    @Test()
    @DisplayName("Erro load file. File not exist")
    public void loadFileNotExist() {

        File file = new File("");
        String out = siteCommands.load(file);
        assertEquals(SiteCommands.ERROR_MESSAGE_FILE, out);

    }

    @Test()
    @DisplayName("Erro load file. IOException")
    public void loadFileIOException() throws IOException {


        File file = Mockito.mock(File.class);

        when(file.exists()).thenReturn(true);
        doThrow(new IOException()).when(site).load(file);


        String out = siteCommands.load(file);
        assertEquals(SiteCommands.ERROR_MESSAGE_FILE_LOAD, out);

    }

    @Test()
    @DisplayName("Load file.")
    public void loadFile() throws IOException {

        File file = Mockito.mock(File.class);
        String mapString = "0";
        String mapSize = "[1] X [1]";

        when(file.exists()).thenReturn(true);
        when(site.stringMap()).thenReturn(mapString);
        when(site.stringMapSize()).thenReturn(mapSize);

        String out = siteCommands.load(file);

        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("map", site.stringMap());
        valuesMap.put("size", site.stringMapSize());

        assertEquals(new StringSubstitutor(valuesMap).replace(SiteCommands.MESSAGE_LOAD_FILE), out);

    }


}
