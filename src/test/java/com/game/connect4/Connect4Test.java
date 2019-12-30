package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.game.connect4.Player.PlayerType;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//@Disabled("Disabled until I figure out why it doesnt work in AzureDevops")
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class Connect4Test {

    private static final String NEW_LINE = System.lineSeparator();

    @Mock
    private Scanner in;
    @Mock
    private PrintWriter out;

    
    @TempDir
    File tmpDir;

    @Test
    public void testGetNumberOfPlayers() throws Exception {
        when(in.next()).thenReturn("1");
        Connect4 connect4 = new Connect4(tmpDir, in, out);
        final List<Integer> choices = Arrays.asList(0, 1, 2);
       
        Integer choice = connect4.getNumberOfPlayers(choices);
        verify(out).print("Select number of players 0, 1 or 2 : ");
        assertEquals(1, choice);
        
    }

    @Test
    public void testGetNumberOfPlayersRetryForInvalidInput()  {
        when(in.next()).thenReturn("d","1");
        Connect4 connect4 = new Connect4(tmpDir, in, out);
        final List<Integer> choices = Arrays.asList(0, 1, 2);
       
        Integer choice = connect4.getNumberOfPlayers(choices);
        verify(out, times(2)).print("Select number of players 0, 1 or 2 : ");
        assertEquals(1, choice);
   
    }

    @Test
    public void testGetPlayerName() {
        when(in.hasNext("[\\w\\s]+")).thenReturn(true);
        when(in.next("[\\w\\s]+")).thenReturn("chris");
        testGetPlayerInput(1);
    }

    @Test
    public void testGetPlayerNameWhiteSpaceInput() {
        when(in.hasNext("[\\w\\s]+")).thenReturn(false,true);
        when(in.next()).thenReturn("");
        when(in.next("[\\w\\s]+")).thenReturn("    ","chris");
        testGetPlayerInput(2);
    }

    @Test
    public void testGetPlayerNameNonWordInput() {
        when(in.hasNext("[\\w\\s]+")).thenReturn(false,true);
        when(in.next()).thenReturn("");
        when(in.next("[\\w\\s]+")).thenReturn("chris");
        testGetPlayerInput(2);
    }

    public void testGetPlayerInput(int times) {
        Connect4 connect4 = new Connect4(tmpDir, in, out);
        Player player = connect4.getPlayer(1);
        verify(out,atLeast(times)).print("Player 1 enter name: ");
        assertEquals("Chris", player.getName());
    }

    @Test
    public void testPrintWinningMessage() {
        Connect4 connect4 = new Connect4(tmpDir, in,out);
        Player player = Player.player1("Chris");
        connect4.printWinningMessage(player);
        verify(out).print(NEW_LINE + "Congratulations Chris, you have won the game." + NEW_LINE);
    }

    public boolean testWillPlayResponse(String input) {
       when(in.next()).thenReturn(input);
        Connect4 connect4 = new Connect4(tmpDir, in, out);
        return connect4.willPlay();
    }

    @Test
    public void tetWontPlayResponse() {
        assertAll(
            () -> assertTrue(testWillPlayResponse("y")),
            () -> assertFalse(testWillPlayResponse("N"))
        );
        verify(out,times(2)).print("Welcome to Connect4, shall we play a game y/N ?");
    }

    @Test
    public void testGetComputerOpponent() {
        Connect4 connect4 = new Connect4(tmpDir, in,out);
        Player player = connect4.getComputerOpponent(2);
        assertEquals(PlayerType.COMPUTER, player.getPlayerType());
    }

    @Test
    public void testGetMove() {
        when(in.next()).thenReturn("1");
        Connect4 connect4 = new Connect4(tmpDir, in, out);
        final List<Integer> moves = Arrays.asList(1, 2, 3, 4, 5);

        assertEquals(1, connect4.getMove(moves, Player.player1("Chris")));
        verify(out).print(NEW_LINE + "Chris (player 1) take a move : ");
    }

    @Test
    public void testGameDataNoPlayersReturnsTwoComputerPlayers() {

        GameData gameData = testGameData(0, in);
        assertAll(
            () -> assertEquals(PlayerType.COMPUTER, gameData.getPlayer1().getPlayerType()),
            () -> assertEquals(PlayerType.COMPUTER, gameData.getPlayer2().getPlayerType())
        );
    }

    @Test
    public void testGameDataOnePlayerReturnsOneHumanAndOneComputerPlayer() {
        when(in.hasNext("[\\w\\s]+")).thenReturn(true);
        when(in.next("[\\w\\s]+")).thenReturn("chris");
        GameData gameData = testGameData(1,in);
        assertAll(
            () -> assertEquals(PlayerType.HUMAN, gameData.getPlayer1().getPlayerType()),
            () -> assertEquals(PlayerType.COMPUTER, gameData.getPlayer2().getPlayerType()),
            () -> assertEquals("Chris", gameData.getPlayer1().getName())
        );
    }

    @Test
    public void testGameDataTwoPlayersReturnsTwoHumanPlayers() {
        when(in.hasNext(anyString())).thenReturn(true,true);
        when(in.next(anyString())).thenReturn("chris","barran");
        GameData gameData = testGameData(2,in);
        assertAll(
            () -> assertEquals(PlayerType.HUMAN, gameData.getPlayer1().getPlayerType()),
            () -> assertEquals(PlayerType.HUMAN, gameData.getPlayer2().getPlayerType()),
            () -> assertEquals("Chris", gameData.getPlayer1().getName()),
            () -> assertEquals("Barran", gameData.getPlayer2().getName())
        );
    }

    @Test
    public void testGameDataOutOfRangeChoiceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> { testGameData(4, in); } );
    }

    public GameData testGameData(Integer numberOfPlayers, Scanner in) {
        Connect4 connect4 = new Connect4(tmpDir, in,out);
        return connect4.getGameData(numberOfPlayers);
    }


    @Test
    public void testGameRuns() throws Exception {
        // No players will play itself.
        when(in.next()).thenReturn("y","0");
        Connect4 connect4 = new Connect4(tmpDir,in,out);
        connect4.run();
        String case1 = NEW_LINE + "Congratulations Computer \\d, you have won the game." + NEW_LINE;
        String case2 = "No more valid moves, game is a tie!";
        verify(out,atLeastOnce()).print(or(matches(case1),matches(case2)));
    }

    @Test
    public void testGameRunsButDecline() throws Exception {
        when(in.next()).thenReturn("N");
        Connect4 connect4 = new Connect4(tmpDir, in,out);
        connect4.run();
        verify(out,times(1)).print("Welcome to Connect4, shall we play a game y/N ?");
    }

    @Test
    public void testGameRunsWithHuman() throws Exception {
        String[] moves = {"1","1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7"};
        when(in.next()).thenReturn("y",moves);
        when(in.hasNext("[\\w\\s]+")).thenReturn(true);
        when(in.next("[\\w\\s]+")).thenReturn("chris");     
        Connect4 connect4 = new Connect4(tmpDir, in,out);
        connect4.run();
        verify(out, atLeastOnce()).print(eq(NEW_LINE + "Chris (player 1) take a move : "));
    } 
}