package com.oracle.clearing.bulldozer.command;

import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.report.Report;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import javax.validation.constraints.Min;

@ShellComponent()
public class BulldozerCommands {

    protected static final String MESSAGE_NOWHERE = "Nowhere to go";
    protected static final String MESSAGE_MOVE = "Bulldozer advance";
    protected static final String MESSAGE_MOVE_PROTECT_TREE = "Sorry you tried to destroy a protected tree." +
            "We have to shut down the simulation";
    protected static final String MESSAGE_MOVE_OUT = "Sorry you tried to walk off the site." +
            "We have to shut down the simulation";
    protected static final String MESSAGE_MOVE_ALL = "Congratulations you completed the simulation.";
    protected static final String MESSAGE_TURN_LEFT = "Bulldozer turn left";
    protected static final String MESSAGE_TURN_RIGHT = "Bulldozer turn right";


    @Autowired
    private Bulldozer bulldozer;
    @Autowired
    private Site site;
    @Autowired
    private Report report;
    @Autowired
    private ShellUtil shellUtil;

    @ShellMethod(value = "Move Bulldozer", key = {"advance", "a"})
    @ShellMethodAvailability("availabilityCheck")
    public String advance(@Min(1) int number) throws ExitRequest {


        try {
            bulldozer.advance(number, site);
        } catch (ProtectAreaTree protectAreaTree) {
            String result = report.create(site, bulldozer);

            shellUtil.print(shellUtil.getErrorMessage(MESSAGE_MOVE_PROTECT_TREE));
            shellUtil.print(shellUtil.getInfoMessage(result));

            throw new ExitRequest(1);

        } catch (OutsideBorder outsideBorder) {
            String result = report.create(site, bulldozer);

            shellUtil.print(shellUtil.getErrorMessage(MESSAGE_MOVE_OUT));
            shellUtil.print(shellUtil.getInfoMessage(result));

            throw new ExitRequest(1);
        }

        if (bulldozer.isCompletedWork(site)) {
            String result = report.create(site, bulldozer);

            shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_MOVE_ALL));
            shellUtil.print(shellUtil.getInfoMessage(result));

            throw new ExitRequest();
        }

        String me = bulldozer.findMe(site);

        shellUtil.print(shellUtil.getInfoMessage(me));

        return shellUtil.getSuccessMessage(MESSAGE_MOVE);

    }


    @ShellMethod(value = "Turn bulldozer left ", key = {"left", "l"})
    @ShellMethodAvailability("availabilityCheck")
    public String turnLeft() {

        bulldozer.turn(-90);
        shellUtil.print(shellUtil.getInfoMessage(bulldozer.findMe(site)));

        return shellUtil.getSuccessMessage(MESSAGE_TURN_LEFT);

    }

    @ShellMethod(value = "Turn bulldozer right ", key = {"right", "r"})
    @ShellMethodAvailability("availabilityCheck")
    public String turnRight() {

        bulldozer.turn(90);
        shellUtil.print(shellUtil.getInfoMessage(bulldozer.findMe(site)));

        return shellUtil.getSuccessMessage(MESSAGE_TURN_RIGHT);
    }

    public Availability availabilityCheck() {
        return site.isEmpty()
                ? Availability.unavailable("you don't load a site")
                : Availability.available();
    }
}
