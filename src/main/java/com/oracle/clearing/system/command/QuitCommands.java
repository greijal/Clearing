package com.oracle.clearing.system.command;


import com.oracle.clearing.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent()
public class QuitCommands {

    protected static final String MESSAGE_QUIT = "Thank you for using the site clearing simulator.";

    @Autowired
    private ShellUtil shellUtil;

    @ShellMethod(value = "Exit the shell.", key = {"quit", "exit", "q"})
    public void quit() {
        shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_QUIT));
        throw new ExitRequest();
    }
}