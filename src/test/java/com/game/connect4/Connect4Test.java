package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class Connect4Test {

    @Mock
    private InputStream in;
    @Mock
    private PrintStream out;

    @Test
    public void testGetNumberOfPlayers() throws Exception {
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Connect4 connect4 = new Connect4(in, out);
        final List<Integer> choices = Arrays.asList(0, 1, 2);
       
        Integer choice = connect4.getNumberOfPlayers(choices);
        verify(out).print("Select number of players 0, 1 or 2 : ");
        assertEquals(1, choice);
        
    }

    @Test
    public void testGetNumberOfPlayersRetryForInvalidInput()  {
        InputStream in = new ByteArrayInputStream("d\n1".getBytes());

        Connect4 connect4 = new Connect4(in, out);
        final List<Integer> choices = Arrays.asList(0, 1, 2);
       
        Integer choice = connect4.getNumberOfPlayers(choices);
        verify(out, times(2)).print("Select number of players 0, 1 or 2 : ");
        assertEquals(1, choice);
   
    }

    @Test
    public void testGetPlayerName() {
        testGetPlayerInput("chris",1);
    }

    @Test
    public void testGetPlayerNameWhiteSpaceInput() {
        testGetPlayerInput("    \nchris",2);
    }

    @Test
    public void testGetPlayerNameNonWordInput() {
        testGetPlayerInput("&*^&\nchris",2);
    }

    public void testGetPlayerInput(String input, int times) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Connect4 connect4 = new Connect4(in, out);
        Player player = connect4.getPlayer(1);
        verify(out,atLeast(times)).printf("Player %s enter name: ", 1);
        assertEquals("Chris", player.getName());
    }
}