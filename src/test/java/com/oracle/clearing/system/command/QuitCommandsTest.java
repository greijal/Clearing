package com.oracle.clearing.system.command;

import com.oracle.clearing.util.ShellUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.shell.ExitRequest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuitCommandsTest {

    @Mock
    private ShellUtil shellUtil;
    @InjectMocks
    private QuitCommands quitCommands;


    @Test(expected = ExitRequest.class)
    public void quit() {
        quitCommands.quit();
        verify(shellUtil, times(1)).getInfoMessage(quitCommands.MESSAGE_QUIT);
    }
}
