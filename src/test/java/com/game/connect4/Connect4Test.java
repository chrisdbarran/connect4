package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.game.connect4.Player.PlayerType;

import org.junit.Ignore;
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

    @Test
    public void testPrintWinningMessage() {
        Connect4 connect4 = new Connect4(in,out);
        Player player = Player.player1("Chris");
        connect4.printWinningMessage(player);
        verify(out).printf("%nCongratulations %s, you have won the game.%n",player.getName());
    }

    public boolean testWillPlayResponse(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Connect4 connect4 = new Connect4(in, out);
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
        Connect4 connect4 = new Connect4(in,out);
        Player player = connect4.getComputerOpponent(2);
        assertEquals(PlayerType.COMPUTER, player.getPlayerType());
    }

    @Test
    public void testGetMove() {
        InputStream in = new ByteArrayInputStream("1".getBytes());
        Connect4 connect4 = new Connect4(in, out);
        final List<Integer> moves = Arrays.asList(1, 2, 3, 4, 5);

        assertEquals(1, connect4.getMove(moves, Player.player1("Chris")));
        verify(out).print("\nChris (player 1) take a move : ");
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
        InputStream in = new ByteArrayInputStream("chris".getBytes());
        GameData gameData = testGameData(1,in);
        assertAll(
            () -> assertEquals(PlayerType.HUMAN, gameData.getPlayer1().getPlayerType()),
            () -> assertEquals(PlayerType.COMPUTER, gameData.getPlayer2().getPlayerType()),
            () -> assertEquals("Chris", gameData.getPlayer1().getName())
        );
    }

    @Test
    public void testGameDataTwoPlayersReturnsTwoHumanPlayers() {
        InputStream in = new ByteArrayInputStream("chris\nbarran".getBytes());
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

    public GameData testGameData(Integer numberOfPlayers, InputStream in) {
        Connect4 connect4 = new Connect4(in,out);
        return connect4.getGameData(numberOfPlayers);
    }

    @Ignore
    public void testGameRuns() throws Exception {
        // No players will play itself.
        InputStream in = new ByteArrayInputStream("y\n0".getBytes());
        Connect4 connect4 = new Connect4(in,out);
        connect4.run();
        // Will it always win? probably not, will have to look at a draw.
        verify(out).printf(eq("%nCongratulations %s, you have won the game.%n"),or(eq("Computer 1"), eq("Computer 2")));
    }

    @Test
    public void testGameRunsButDecline() throws Exception {
        InputStream in = new ByteArrayInputStream("n".getBytes());
        Connect4 connect4 = new Connect4(in,out);
        connect4.run();
    }

    @Test
    public void testGameRunsWithHuman() throws Exception {
        InputStream in = new ByteArrayInputStream("y\n1\nchris\n1\n2\n3\n4\n5\n6\n1\n2\n3\n4\n5\n6\n1\n2\n3\n4\n5\n6\n1\n2\n3\n4\n5\n6\n1\n2\n3\n4\n5\n6\n1\n2\n3\n4\n5\n6".getBytes());
        Connect4 connect4 = new Connect4(in,out);
        connect4.run();
        verify(out, atLeastOnce()).print(eq("\nChris (player 1) take a move : "));
    } 

    @Ignore
    public void testConnect4Main() throws Exception {
        String[] args = null;
        final InputStream originalIn = System.in;
        final PrintStream originalOut = System.out;
        
        InputStream in = new ByteArrayInputStream("y\n0".getBytes());

        System.setIn(in);
        System.setOut(out);
        Connect4.main(args);
        verify(out).printf(eq("%nCongratulations %s, you have won the game.%n"),or(eq("Computer 1"), eq("Computer 2")));
        System.setIn(originalIn);
        System.setOut(originalOut);
    }
}