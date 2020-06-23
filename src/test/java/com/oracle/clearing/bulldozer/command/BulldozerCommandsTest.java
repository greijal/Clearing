package com.oracle.clearing.bulldozer.command;


import com.oracle.clearing.bulldozer.Bulldozer;
import com.oracle.clearing.report.Report;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.ShellUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.shell.Availability;
import org.springframework.shell.ExitRequest;

import static com.oracle.clearing.bulldozer.command.BulldozerCommands.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BulldozerCommandsTest {

    @Mock
    private Site site;
    @Mock
    private Bulldozer bulldozer;
    @Mock
    private ShellUtil shellUtil;
    @Mock
    private Report report;
    @InjectMocks
    private BulldozerCommands bulldozerCommands;

    @Test
    public void advance() throws ProtectAreaTree, OutsideBorder {

        String meOnMap = "MAP";

        when(bulldozer.findMe(site)).thenReturn(meOnMap);
        when(shellUtil.getSuccessMessage(MESSAGE_MOVE)).thenReturn(MESSAGE_MOVE);
        when(shellUtil.getInfoMessage(meOnMap)).thenReturn(meOnMap);

        String result = bulldozerCommands.advance(3);

        assertEquals(MESSAGE_MOVE, result);

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_MOVE);
        verify(shellUtil, times(1)).getInfoMessage(meOnMap);

        verify(bulldozer, times(1)).findMe(site);


    }

    @Test(expected = ExitRequest.class)
    public void advanceProtectAreaTree() throws ProtectAreaTree, OutsideBorder {

        String reportResult = "REPORT";
        Mockito.doThrow(new ProtectAreaTree()).when(bulldozer).advance(3, site);

        when(report.create(site, bulldozer)).thenReturn(reportResult);

        bulldozerCommands.advance(3);

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getErrorMessage(MESSAGE_MOVE_PROTECT_TREE);
        verify(shellUtil, times(1)).getInfoMessage(reportResult);
        verify(report, times(1)).create(site, bulldozer);

    }

    @Test(expected = ExitRequest.class)
    public void advanceOutside() throws ProtectAreaTree, OutsideBorder {

        String reportResult = "REPORT";

        doThrow(new OutsideBorder()).when(bulldozer).advance(3, site);
        when(report.create(site, bulldozer)).thenReturn(reportResult);

        bulldozerCommands.advance(3);

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getErrorMessage(MESSAGE_MOVE_OUT);
        verify(shellUtil, times(1)).getInfoMessage(reportResult);
        verify(report, times(1)).create(site, bulldozer);

    }

    @Test(expected = ExitRequest.class)
    public void advanceAll() throws ProtectAreaTree, OutsideBorder {

        when(bulldozer.isCompletedWork(site)).thenReturn(true);

        bulldozerCommands.advance(3);

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_MOVE_ALL);
        verify(report, times(1)).create(site, bulldozer);

    }


    @Test
    public void turnLeft() {


        String meOnMap = "MAP_SITE";

        when(bulldozer.findMe(site)).thenReturn(meOnMap);
        when(shellUtil.getInfoMessage(meOnMap)).thenReturn(meOnMap);
        when(shellUtil.getSuccessMessage(MESSAGE_TURN_LEFT))
                .thenReturn(MESSAGE_TURN_LEFT);

        String result = bulldozerCommands.turnLeft();

        assertEquals(MESSAGE_TURN_LEFT, result);
        verify(bulldozer, times(1)).turn(-90);
        verify(shellUtil, times(1)).getInfoMessage(meOnMap);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_TURN_LEFT);

    }

    @Test
    public void turnRight() {

        String meOnMap = "MAP_SITE";

        when(bulldozer.findMe(site)).thenReturn(meOnMap);
        when(shellUtil.getInfoMessage(meOnMap)).thenReturn(meOnMap);
        when(shellUtil.getSuccessMessage(MESSAGE_TURN_RIGHT))
                .thenReturn(MESSAGE_TURN_RIGHT);

        String result = bulldozerCommands.turnRight();

        assertEquals(MESSAGE_TURN_RIGHT, result);
        verify(bulldozer, times(1)).turn(90);
        verify(shellUtil, times(1)).getInfoMessage(meOnMap);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_TURN_RIGHT);

    }

    @Test
    public void availabilityCheckAvailableTest() {
        when(site.isEmpty()).thenReturn(false);
        Availability availability = bulldozerCommands.availabilityCheck();
        assertTrue(availability.isAvailable());
    }

    @Test
    public void availabilityCheckUnavailableTest() {
        when(site.isEmpty()).thenReturn(true);
        Availability availability = bulldozerCommands.availabilityCheck();
        assertFalse(availability.isAvailable());
    }

}
