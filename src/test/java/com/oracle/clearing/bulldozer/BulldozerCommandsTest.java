package com.oracle.clearing.bulldozer;


import com.oracle.clearing.report.Report;
import com.oracle.clearing.site.Site;
import com.oracle.clearing.site.exception.OutsideBorder;
import com.oracle.clearing.site.exception.ProtectAreaTree;
import com.oracle.clearing.util.ShellUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.table.Table;
import org.ujmp.core.charmatrix.impl.ArrayDenseCharMatrix2D;

import static com.oracle.clearing.bulldozer.BulldozerCommands.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Bulldozer Commands")
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
    @DisplayName("Null site")
    public void advanceNullSite() throws ProtectAreaTree, OutsideBorder {


        when(site.getMatrix()).thenReturn(null);

        bulldozerCommands.advance(1);

        verify(bulldozer, never()).advance(1, site);
        verify(shellUtil, times(1)).getWarningMessage(MESSAGE_MATRIX_NUL);
    }


    @Test
    @DisplayName("Do not move 0")
    public void advance0() throws ProtectAreaTree, OutsideBorder {

        bulldozerCommands.advance(0);

        verify(bulldozer, never()).advance(0, site);
        verify(shellUtil, times(1)).getWarningMessage(MESSAGE_NOWHERE);
    }


    @Test
    @DisplayName("Move")
    public void advance() throws ProtectAreaTree, OutsideBorder {

        when(site.getMatrix()).thenReturn(mock(ArrayDenseCharMatrix2D.class));

        bulldozerCommands.advance(3);

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_MOVE);
        verify(bulldozer, times(1)).findMe(site);

    }

    @Test
    @DisplayName("Move on protect tree")
    public void advanceProtectAreaTree() throws ProtectAreaTree, OutsideBorder {

        Table reportTable = mock(Table.class);

        Mockito.doThrow(new ProtectAreaTree()).when(bulldozer).advance(3, site);

        when(site.getMatrix()).thenReturn(mock(ArrayDenseCharMatrix2D.class));
        when(report.report(site, bulldozer, true)).thenReturn(reportTable);

        assertThrows(ExitRequest.class, () -> bulldozerCommands.advance(3));

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getErrorMessage(MESSAGE_MOVE_PROTECT_TREE);
        verify(report, times(1)).report(site, bulldozer, true);

    }

    @Test
    @DisplayName("Move Outside Border")
    public void advanceOutside() throws ProtectAreaTree, OutsideBorder {

        Table reportTable = mock(Table.class);

        Mockito.doThrow(new OutsideBorder()).when(bulldozer).advance(3, site);
        when(report.report(site, bulldozer, false)).thenReturn(reportTable);
        when(site.getMatrix()).thenReturn(mock(ArrayDenseCharMatrix2D.class));


        assertThrows(ExitRequest.class, () -> bulldozerCommands.advance(3));

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getErrorMessage(MESSAGE_MOVE_OUT);
        verify(report, times(1)).report(site, bulldozer, false);

    }

    @Test
    @DisplayName("Move all")
    public void advanceAll() throws ProtectAreaTree, OutsideBorder {

        Table reportTable = mock(Table.class);

        when(bulldozer.isCompletedWork(site)).thenReturn(true);
        when(report.report(site, bulldozer, false)).thenReturn(reportTable);
        when(site.getMatrix()).thenReturn(mock(ArrayDenseCharMatrix2D.class));


        assertThrows(ExitRequest.class, () -> bulldozerCommands.advance(3));

        verify(bulldozer, times(1)).advance(3, site);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_MOVE_ALL);
        verify(report, times(1)).report(site, bulldozer, false);

    }


    @Test
    @DisplayName("Turn Left")
    public void turnLeft() {

        bulldozerCommands.turnLeft();
        verify(bulldozer, times(1)).turn(-90);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_TURN_LEFT);

    }


    @Test
    @DisplayName("Turn Left")
    public void turnRight() {

        bulldozerCommands.turnRight();
        verify(bulldozer, times(1)).turn(90);
        verify(shellUtil, times(1)).getSuccessMessage(MESSAGE_TURN_RIGHT);

    }

}
