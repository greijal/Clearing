package com.oracle.clearing.util;

import org.jline.terminal.Terminal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShellUtilTest {

    @Mock
    private Terminal terminal;
    @InjectMocks
    private ShellUtil shellUtil;

    @Test
    public void getInfoMessage() {
        assertEquals("\u001B[37mTEST\u001B[0m", shellUtil.getInfoMessage("TEST"));
    }

    @Test
    public void getSuccessMessage() {
        assertEquals("\u001B[32mTEST\u001B[0m", shellUtil.getSuccessMessage("TEST"));
    }

    @Test
    public void getWarningMessage() {
        assertEquals("\u001B[33mTEST\u001B[0m", shellUtil.getWarningMessage("TEST"));
    }

    @Test
    public void getErrorMessage() {
        assertEquals("\u001B[31mTEST\u001B[0m", shellUtil.getErrorMessage("TEST"));
    }

    @Test
    public void print() {

        when(terminal.writer()).thenReturn(mock(PrintWriter.class));

        shellUtil.print("TEST");

        verify(terminal, times(1)).writer();
        verify(terminal, times(1)).flush();
    }
}
