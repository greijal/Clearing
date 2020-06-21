package com.oracle.clearing.site.command;

import com.oracle.clearing.site.Site;
import com.oracle.clearing.util.ShellUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;

import java.io.File;
import java.io.IOException;

import static com.oracle.clearing.site.command.SiteCommands.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SiteCommandsTest {

    @Mock
    private Site site;
    @Mock
    private ShellUtil shellUtil;
    @InjectMocks
    private SiteCommands siteCommands;


    @Test
    public void loadFileNotExist() throws IOException {

        File file = new File("");

        siteCommands.load(file);

        verify(shellUtil, times(1)).getErrorMessage(ERROR_MESSAGE_FILE);
        verify(site, never()).load(file);

    }

    @Test()
    public void loadFileSiteNotNull() throws IOException {

        File file = mock(File.class);

        when(site.isEmpty()).thenReturn(true);
        when(file.exists()).thenReturn(true);

        siteCommands.load(file);
        verify(shellUtil, times(1)).getErrorMessage(ERROR_MESSAGE_SITE_LOAD);
        verify(site, never()).load(file);

    }

    @Test()
    public void loadFileIOException() throws IOException {


        File file = Mockito.mock(File.class);

        when(file.exists()).thenReturn(true);
        doThrow(new IOException()).when(site).load(file);

        siteCommands.load(file);

        verify(shellUtil, times(1)).getErrorMessage(ERROR_MESSAGE_FILE_LOAD);
        verify(site, times(1)).load(file);


    }

    @Test()
    public void loadFile() throws IOException {

        File file = Mockito.mock(File.class);
        String mapString = "0";

        when(file.exists()).thenReturn(true);
        when(site.stringMap()).thenReturn(mapString);

        siteCommands.load(file);


        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_LOAD_FILE);
        verify(shellUtil, times(1)).getInfoMessage(MESSAGE_START_QUESTION);
        verify(shellUtil, times(1)).getInfoMessage("0");
        verify(site, times(1)).load(file);


    }


}
