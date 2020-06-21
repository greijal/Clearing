package com.oracle.clearing.util;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShellUtil {

    @Autowired
    private Terminal terminal;

    private String getColored(String message, int color) {
        return (new AttributedStringBuilder())
                .append(message, AttributedStyle.DEFAULT.foreground(color)).toAnsi();
    }

    public String getInfoMessage(String message) {
        return getColored(message, AttributedStyle.CYAN);
    }

    public String getSuccessMessage(String message) {
        return getColored(message, AttributedStyle.GREEN);
    }

    public String getWarningMessage(String message) {
        return getColored(message, AttributedStyle.YELLOW);
    }

    public String getErrorMessage(String message) {
        return getColored(message, AttributedStyle.RED);
    }

    public void print(String message) {
        terminal.writer().println(message);
        terminal.flush();
    }

}
