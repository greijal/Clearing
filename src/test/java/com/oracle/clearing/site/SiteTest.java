package com.oracle.clearing.site;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Site")
public class SiteTest {

    @InjectMocks
    private Site site;

    @Test()
    @DisplayName("Erro load file. File not exist")
    public void loadFileNotExist() {

        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            File file = new File("");
            site.load(file);
        });
    }

    @Test()
    @DisplayName("Load File")
    public void load() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("site.txt").getFile());
        site.load(file);
    }


}
