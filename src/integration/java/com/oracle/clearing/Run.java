package com.oracle.clearing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.clearing.script.Script;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.result.DefaultResultHandler;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false",
        "spring.shell.script.enabled=false"
})
public class Run {

    @Autowired
    private Shell shell;
    @Autowired
    private DefaultResultHandler resultHandler;
    private Script script;

    @Before
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("script.jon").getFile());

        this.script = objectMapper.readValue(file, Script.class);
        this.script.order();
    }

    @Test
    public void loadSite() throws IOException {

        this.script.getSteps().stream().forEach(step -> {
            Object result = shell.evaluate(() -> step.getCommand());
            assertEquals(step.getResult(), result.toString());
            resultHandler.handleResult(result);
        });
    }

}
