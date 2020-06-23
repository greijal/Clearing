package com.oracle.clearing.site.command;

import com.oracle.clearing.site.Site;
import com.oracle.clearing.util.ShellUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.shell.Availability;

import java.io.File;
import java.io.IOException;

import static com.oracle.clearing.site.command.SiteCommands.*;
import static org.junit.Assert.*;
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

        when(shellUtil.getErrorMessage(ERROR_MESSAGE_FILE))
                .thenReturn(ERROR_MESSAGE_FILE);

        String result = siteCommands.load(file);

        verify(shellUtil, times(1)).getErrorMessage(ERROR_MESSAGE_FILE);
        verify(site, never()).load(file);

        assertEquals(ERROR_MESSAGE_FILE, result);

    }

    @Test(expected = IOException.class)
    public void loadFileIOException() throws IOException {

        File file = Mockito.mock(File.class);

        when(file.exists()).thenReturn(true);

        when(shellUtil.getErrorMessage(ERROR_MESSAGE_FILE_LOAD))
                .thenReturn(ERROR_MESSAGE_FILE_LOAD);

        doThrow(new IOException()).when(site).load(file);

        siteCommands.load(file);

        verify(shellUtil, times(1)).getErrorMessage(ERROR_MESSAGE_FILE_LOAD);
        verify(site, times(1)).load(file);


    }

    @Test()
    public void loadFile() throws IOException {

        File file = Mockito.mock(File.class);
        String mapString = "MAP_SITE";


        when(file.exists()).thenReturn(true);
        when(site.stringMap()).thenReturn(mapString);
        when(shellUtil.getSuccessMessage(MESSAGE_LOAD_FILE))
                .thenReturn(MESSAGE_LOAD_FILE);

        String result = siteCommands.load(file);

        assertEquals(MESSAGE_LOAD_FILE, result);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_LOAD_FILE);
        verify(shellUtil, times(1)).getInfoMessage(mapString);
        verify(site, times(1)).load(file);

    }

    @Test
    public void availabilityCheckAvailableTest() {
        when(site.isEmpty()).thenReturn(true);
        Availability availability = siteCommands.availabilityCheck();
        assertTrue(availability.isAvailable());

    }

    @Test
    public void availabilityCheckUnavailableTest() {
        when(site.isEmpty()).thenReturn(false);
        Availability availability = siteCommands.availabilityCheck();
        assertFalse(availability.isAvailable());
    }
}
