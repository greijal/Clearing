package com.oracle.clearing.site.command;

import com.oracle.clearing.site.Site;
import com.oracle.clearing.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;


@ShellComponent()
public class SiteCommands {

    protected static final String ERROR_MESSAGE_FILE = "File not exist. Please try again";
    protected static final String ERROR_MESSAGE_FILE_LOAD = "Error when loading file. Check the file used";
    protected static final String MESSAGE_LOAD_FILE = "Your new site has been successfully uploaded !\nShall we get started ?";

    @Autowired
    private Site site;
    @Autowired
    private ShellUtil shellUtil;

    @ShellMethod(value = "Load new site map file", key = {"load"})
    @ShellMethodAvailability("availabilityCheck")
    public String load(@NotNull File file) throws IOException {

        if (!file.exists()) {
            return shellUtil.getErrorMessage(ERROR_MESSAGE_FILE);
        }

        try {
            site.load(file);
        } catch (IOException e) {
            shellUtil.print(shellUtil.getErrorMessage(ERROR_MESSAGE_FILE_LOAD));
            throw e;
        }

        shellUtil.print(shellUtil.getInfoMessage(site.stringMap()));

        return shellUtil.getSuccessMessage(MESSAGE_LOAD_FILE);
    }

    public Availability availabilityCheck() {
        return !site.isEmpty()
                ? Availability.unavailable("you've already uploaded a file.")
                : Availability.available();
    }
}
