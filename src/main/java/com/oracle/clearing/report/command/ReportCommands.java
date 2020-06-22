package com.oracle.clearing.report.command;

import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.report.Report;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class ReportCommands {

    @Autowired
    private Bulldozer bulldozer;
    @Autowired
    private Site site;
    @Autowired
    private Report report;
    @Autowired
    private ShellUtil shellUtil;

    @ShellMethod(value = "Print report", key = {"report"})
    @ShellMethodAvailability("availabilityCheck")
    public String report() {
        return shellUtil.getInfoMessage(report.create(site, bulldozer));
    }

    public Availability availabilityCheck() {
        return site.isEmpty()
                ? Availability.unavailable("you don't load a site")
                : Availability.available();
    }

}
