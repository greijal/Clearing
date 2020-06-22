package com.oracle.clearing.report;

import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.report.command.ReportCommands;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.util.ShellUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.shell.Availability;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportCommandsTest {

    @Mock
    private Bulldozer bulldozer;
    @Mock
    private Site site;
    @Mock
    private Report report;
    @Mock
    private ShellUtil shellUtil;
    @InjectMocks
    private ReportCommands reportCommands;

    @Test
    public void report() {

        String reportStr = "REPORT";
        when(report.create(site, bulldozer)).thenReturn(reportStr);
        when(shellUtil.getInfoMessage(reportStr)).thenReturn(reportStr);

        String result = reportCommands.report();
        assertEquals(reportStr, result);

        verify(shellUtil, times(1)).getInfoMessage(reportStr);
        verify(report, times(1)).create(any(), any());

    }

    @Test
    public void availabilityCheckAvailableTest() {
        when(site.isEmpty()).thenReturn(false);
        Availability availability = reportCommands.availabilityCheck();
        assertTrue(availability.isAvailable());
    }

    @Test
    public void availabilityCheckUnavailableTest() {
        when(site.isEmpty()).thenReturn(true);
        Availability availability = reportCommands.availabilityCheck();
        assertFalse(availability.isAvailable());
    }

}
