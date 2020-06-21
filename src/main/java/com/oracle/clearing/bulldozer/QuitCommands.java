package com.oracle.clearing.bulldozer;


import com.oracle.clearing.report.Report;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;


@ShellComponent()
public class QuitCommands {

    protected static final String MESSAGE_QUIT = "Thank you for using the site clearing simulator.";

    @Autowired
    private Report report;
    @Autowired
    private Bulldozer bulldozer;
    @Autowired
    private Site site;
    @Autowired
    private ShellUtil shellUtil;

    @ShellMethod(value = "Exit the shell.", key = {"quit", "exit","q"})
    public void quit() {

        Table result = report.report(site, bulldozer, false);

        shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_QUIT));
        shellUtil.print(shellUtil.getInfoMessage(result.render(80)));

        throw new ExitRequest();
    }
}