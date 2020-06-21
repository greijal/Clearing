package com.oracle.clearing.bulldozer;

import com.oracle.clearing.report.Report;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;

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

    @ShellMethod(value = "Move Bulldozer", key = {"advance", "a"}, group = "bulldozer")
    public void advance(@NotNull Integer number) throws ExitRequest {

        if (number == 0) {
            shellUtil.print(shellUtil.getWarningMessage(MESSAGE_NOWHERE));
            return;
        }

        if (site.getMatrix() == null) {
            shellUtil.print(shellUtil.getWarningMessage(MESSAGE_MATRIX_NUL));
            return;
        }

        try {
            bulldozer.advance(number, site);
        } catch (ProtectAreaTree protectAreaTree) {
            Table result = report.report(site, bulldozer, true);

            shellUtil.print(shellUtil.getErrorMessage(MESSAGE_MOVE_PROTECT_TREE));
            shellUtil.print(shellUtil.getInfoMessage(result.render(80)));

            throw new ExitRequest(1);

        } catch (OutsideBorder outsideBorder) {
            Table result = report.report(site, bulldozer, false);

            shellUtil.print(shellUtil.getErrorMessage(MESSAGE_MOVE_OUT));
            shellUtil.print(shellUtil.getInfoMessage(result.render(80)));

            throw new ExitRequest(1);
        }

        if (bulldozer.isCompletedWork(site)) {
            Table result = report.report(site, bulldozer, false);

            shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_MOVE_ALL));
            shellUtil.print(shellUtil.getInfoMessage(result.render(80)));

            throw new ExitRequest();
        }

        shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_MOVE));
        shellUtil.print(shellUtil.getInfoMessage(bulldozer.findMe(site)));

    }


    @ShellMethod(value = "Turn bulldozer left ", key = {"left", "l"}, group = "bulldozer")
    public void turnLeft() {

        if (site.getMatrix() == null) {
            shellUtil.print(shellUtil.getWarningMessage(MESSAGE_MATRIX_NUL));
            return;
        }

        bulldozer.turn(-90);
        shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_TURN_LEFT));
        shellUtil.print(shellUtil.getInfoMessage(bulldozer.findMe(site)));


    }

    @ShellMethod(value = "Turn bulldozer right ", key = {"right", "r"}, group = "bulldozer")
    public void turnRight() {

        if (site.getMatrix() == null) {
            shellUtil.print(shellUtil.getWarningMessage(MESSAGE_MATRIX_NUL));
            return;
        }

        bulldozer.turn(90);
        shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_TURN_RIGHT));
        shellUtil.print(shellUtil.getInfoMessage(bulldozer.findMe(site)));


    }

}
