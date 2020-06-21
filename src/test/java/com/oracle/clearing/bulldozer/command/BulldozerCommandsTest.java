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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.shell.ExitRequest;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;

import static com.oracle.clearing.bulldozer.command.BulldozerCommands.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BulldozerCommandsTest {

    @InjectMocks
    private BulldozerCommands bulldozerCommands;
    @Mock
    private Site site;
    @Mock
    private Bulldozer bulldozer;
    @Mock
    private ShellUtil shellUtil;
    @Mock
    private Report report;

    @Test
    public void advanceNullSite() throws ProtectAreaTree, OutsideBorder {


        when(site.isEmpty()).thenReturn(true);

        bulldozerCommands.advance(1);

        verify(bulldozer, never()).advance(1, site);
        verify(shellUtil, times(1)).getWarningMessage(MESSAGE_MATRIX_NUL);
    }


    @Test
    public void advance0() throws ProtectAreaTree, OutsideBorder {

        bulldozerCommands.advance(0);

        verify(bulldozer, never()).advance(0, site);
        verify(shellUtil, times(1)).getWarningMessage(MESSAGE_NOWHERE);
    }


    @Test
    public void advance() throws ProtectAreaTree, OutsideBorder {

        when(site.isEmpty()).thenReturn(false);

        bulldozerCommands.advance(3);

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_MOVE);
        verify(bulldozer, times(1)).findMe(site);

    }

    @Test(expected = ExitRequest.class)
    public void advanceProtectAreaTree() throws ProtectAreaTree, OutsideBorder {


        Mockito.doThrow(new ProtectAreaTree()).when(bulldozer).advance(3, site);

        when(site.isEmpty()).thenReturn(false);
        when(report.createReport(site, bulldozer, true)).thenReturn("reportTable");

        bulldozerCommands.advance(3);

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getErrorMessage(MESSAGE_MOVE_PROTECT_TREE);
        verify(report, times(1)).createReport(site, bulldozer, true);

    }

    @Test(expected = ExitRequest.class)
    public void advanceOutside() throws ProtectAreaTree, OutsideBorder {

        Mockito.doThrow(new OutsideBorder()).when(bulldozer).advance(3, site);
        when(report.createReport(site, bulldozer, false)).thenReturn("");
        when(site.isEmpty()).thenReturn(false);

        bulldozerCommands.advance(3);

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getErrorMessage(MESSAGE_MOVE_OUT);
        verify(report, times(1)).createReport(site, bulldozer, false);

    }

    @Test(expected = ExitRequest.class)
    public void advanceAll() throws ProtectAreaTree, OutsideBorder {


        when(bulldozer.isCompletedWork(site)).thenReturn(true);

        when(site.isEmpty()).thenReturn(false);

        bulldozerCommands.advance(3);

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_MOVE_ALL);
        verify(report, times(1)).createReport(site, bulldozer, false);

    }


    @Test
    public void turnLeft() {

        when(site.isEmpty()).thenReturn(false);

        bulldozerCommands.turnLeft();
        verify(bulldozer, times(1)).turn(-90);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_TURN_LEFT);

    }

    @Test
    public void turnLeftNullSite() {

        when(site.isEmpty()).thenReturn(true);

        bulldozerCommands.turnLeft();
        verify(bulldozer, never()).turn(anyInt());
        verify(shellUtil, times(1)).getWarningMessage(MESSAGE_MATRIX_NUL);


    }


    @Test
    public void turnRightNullSite() {

        when(site.isEmpty()).thenReturn(true);

        bulldozerCommands.turnRight();
        verify(bulldozer, never()).turn(anyInt());
        verify(shellUtil, times(1)).getWarningMessage(MESSAGE_MATRIX_NUL);

    }

    @Test
    public void turnRight() {

        when(site.isEmpty()).thenReturn(false);

        bulldozerCommands.turnRight();
        verify(bulldozer, times(1)).turn(90);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_TURN_RIGHT);

    }

}
