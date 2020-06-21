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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        reportCommands.report();
        verify(report, times(1)).createReport(any(), any(), eq(false));
    }

}
