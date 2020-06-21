package com.oracle.clearing.bulldozer.command;

import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.report.Report;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.validation.constraints.NotNull;

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
    protected static final String MESSAGE_MATRIX_NUL = "Oops... you didn't upload the site first. " +
            "Try execute load command";

    @Autowired
    private Bulldozer bulldozer;
    @Autowired
    private Site site;
    @Autowired
    private Report report;
    @Autowired
    private ShellUtil shellUtil;

    @ShellMethod(value = "Move Bulldozer", key = {"advance", "a"})
    public void advance(@NotNull Integer number) throws ExitRequest {

        if (number == 0) {
            shellUtil.print(shellUtil.getWarningMessage(MESSAGE_NOWHERE));
            return;
        }

        if (site.isEmpty()) {
            shellUtil.print(shellUtil.getWarningMessage(MESSAGE_MATRIX_NUL));
            return;
        }

        try {
            bulldozer.advance(number, site);
        } catch (ProtectAreaTree protectAreaTree) {
            String result = report.createReport(site, bulldozer, true);

            shellUtil.print(shellUtil.getErrorMessage(MESSAGE_MOVE_PROTECT_TREE));
            shellUtil.print(shellUtil.getInfoMessage(result));

            throw new ExitRequest(1);

        } catch (OutsideBorder outsideBorder) {
            String result = report.createReport(site, bulldozer, false);

            shellUtil.print(shellUtil.getErrorMessage(MESSAGE_MOVE_OUT));
            shellUtil.print(shellUtil.getInfoMessage(result));

            throw new ExitRequest(1);
        }

        if (bulldozer.isCompletedWork(site)) {
            String result = report.createReport(site, bulldozer, false);

            shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_MOVE_ALL));
            shellUtil.print(shellUtil.getInfoMessage(result));

            throw new ExitRequest();
        }

        shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_MOVE));
        shellUtil.print(shellUtil.getInfoMessage(bulldozer.findMe(site)));

    }


    @ShellMethod(value = "Turn bulldozer left ", key = {"left", "l"})
    public void turnLeft() {

        if (site.isEmpty()) {
            shellUtil.print(shellUtil.getWarningMessage(MESSAGE_MATRIX_NUL));
            return;
        }

        bulldozer.turn(-90);
        shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_TURN_LEFT));
        shellUtil.print(shellUtil.getInfoMessage(bulldozer.findMe(site)));


    }

    @ShellMethod(value = "Turn bulldozer right ", key = {"right", "r"})
    public void turnRight() {

        if (site.isEmpty()) {
            shellUtil.print(shellUtil.getWarningMessage(MESSAGE_MATRIX_NUL));
            return;
        }

        bulldozer.turn(90);
        shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_TURN_RIGHT));
        shellUtil.print(shellUtil.getInfoMessage(bulldozer.findMe(site)));


    }

}
