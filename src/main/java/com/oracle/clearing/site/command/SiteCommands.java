package com.oracle.clearing.site.command;

import com.oracle.clearing.site.Site;
import com.oracle.clearing.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;


@ShellComponent()
public class SiteCommands {

    protected static final String ERROR_MESSAGE_FILE = "File not exist. Please try again";
    protected static final String ERROR_MESSAGE_SITE_LOAD = "Oops... But you've already uploaded a file.";
    protected static final String ERROR_MESSAGE_FILE_LOAD = "Error when loading file. Check the file used";
    protected static final String MESSAGE_LOAD_FILE = "Your new site has been successfully uploaded !";
    protected static final String MESSAGE_START_QUESTION = "Shall we get started?";


    @Autowired
    private Site site;
    @Autowired
    private ShellUtil shellUtil;

    @ShellMethod(value = "Load new site map file", key = {"load"})
    public void load(@NotNull File file) {

        if (!file.exists()) {
            shellUtil.print(shellUtil.getErrorMessage(ERROR_MESSAGE_FILE));
            return;
        }

        if (site.isEmpty()) {
            shellUtil.print(shellUtil.getErrorMessage(ERROR_MESSAGE_SITE_LOAD));
            return;
        }

        try {
            site.load(file);
        } catch (IOException e) {
            shellUtil.print(shellUtil.getErrorMessage(ERROR_MESSAGE_FILE_LOAD));
            return;
        }

        shellUtil.print(shellUtil.getSuccessMessage(MESSAGE_LOAD_FILE));
        shellUtil.print(shellUtil.getInfoMessage(site.stringMap()));
        shellUtil.print(shellUtil.getInfoMessage(MESSAGE_START_QUESTION));

    }
}
