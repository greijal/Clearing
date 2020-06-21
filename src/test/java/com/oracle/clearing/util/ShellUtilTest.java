package com.oracle.clearing.util;

import org.jline.terminal.Terminal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test ShellUtil")
public class ShellUtilTest {


    @Mock
    private Terminal terminal;
    @InjectMocks
    private ShellUtil shellUtil;

    @Test
    @DisplayName("Test colored info message")
    public void getInfoMessage() {
        assertEquals("\u001B[36mTEST\u001B[0m", shellUtil.getInfoMessage("TEST"));
    }


    @Test
    @DisplayName("Test success info message")
    public void getSuccessMessage() {
        assertEquals("\u001B[32mTEST\u001B[0m", shellUtil.getSuccessMessage("TEST"));
    }

    @Test
    @DisplayName("Test warning info message")
    public void getWarningMessage() {
        assertEquals("\u001B[33mTEST\u001B[0m", shellUtil.getWarningMessage("TEST"));
    }

    @Test
    @DisplayName("Test error info message")
    public void getErrorMessage() {
        assertEquals("\u001B[31mTEST\u001B[0m", shellUtil.getErrorMessage("TEST"));
    }

    @Test
    @DisplayName("Test error info message")
    public void print() {

        when(terminal.writer()).thenReturn(mock(PrintWriter.class));

        shellUtil.print("TEST");

        verify(terminal, times(1)).writer();
        verify(terminal, times(1)).flush();
    }
}
