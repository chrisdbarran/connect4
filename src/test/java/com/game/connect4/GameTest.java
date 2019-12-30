package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class GameTest {

    @TempDir
    File tmpDir;

    Game game;

    @BeforeEach
    public void setup() {
        game = TestConfig.buildDefaultGame(tmpDir);
    }

    @Test
    void newGameWithPlayers() {
        assertNotNull(game, "Game should initialise with players");
        assertEquals("player1", game.player1().getName());
        assertEquals("player2", game.player2().getName());
    }

    @Test
    void newGameStartsWithPlayer1() {
        assertEquals(game.player1(),game.who(), "Game should start with player1");
    }

    @Test
    void newGameStartsWithEmptyBoard() {
        Board emptyBoard = new Board();
        assertEquals(emptyBoard, game.board());
    } 

    @Test
    void saveGameWritesGameToFile() throws Exception {

        File saveGame = TestConfig.getTestSaveGame(tmpDir, "gameData.json");
        
        List<String> testFile = TestConfig.loadTestFileLines("testSaveGame.json");
        
        game.saveGame(null);
        assertAll(
            () -> assertTrue(saveGame.exists()),
            () -> assertLinesMatch(testFile, Files.readAllLines(saveGame.toPath())));
    }


    @Test
    void loadGameReadsGameFromFile() throws Exception {
        
        TestConfig.copyTestFileToTempFolder(tmpDir, "testLoadGame.json");
        
        game.loadGame("testLoadGame.json");
        
        assertAll(
            () -> assertEquals("Stefan",game.player1().getName()),
            () -> assertEquals("Mary", game.player2().getName()),
            () -> assertEquals(game.player2().getPlayerId(), game.who().getPlayerId()),
            () -> assertEquals(testBoard().getCells(), game.board().getCells()));
    }


    private Board testBoard() {
        return new Board(new int[][] {
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,1,0,0},
            {2,2,1,1,2,1,0}
        });
    }

    @Test
    public void suggestMovePlayerOneWin()
    {
        Game game = TestConfig.buildGameWithBoard(tmpDir, playerOneWinNextMove());
        assertEquals(3, game.suggestMove(game.player1()));
    }

    @Test
    public void suggestMoveBlockPlayerTwoWin()
    {
        Game game = TestConfig.buildGameWithBoard(tmpDir, playerTwoWinNextMove());
        assertEquals(2, game.suggestMove(game.player1()));
    }

    private Board playerOneWinNextMove() {
        return new Board(new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,1,0,0},
            {0,0,2,1,2,0,0},
            {0,1,2,2,1,0,0},
            {2,1,1,2,2,1,2}
        });
    }

    private Board playerTwoWinNextMove() {
        return new Board(new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,1,0,0},
            {0,2,2,1,2,0,0},
            {0,1,2,2,1,0,0},
            {2,1,1,1,2,2,2}
        });
    }



    @Test
    public void testGetOpponent() {
        Player player1 = game.player1();
        Player player2 = game.player2();

        assertAll(
            () -> assertEquals(player2, game.getOpponent(player1)),
            () -> assertEquals(player1, game.getOpponent(player2))
        );
    }

    @Test
    public void testGetWho() {
        assertEquals(game.player1(), game.who());
    }

    @Test
    public void testSetWho() {
        Player player2 = game.player2();
        game.who(player2);
        assertEquals(player2, game.who());
    }

    @Disabled
    @Test
    public void testRandomValidMoveReturnsAValidMove() {
        LinkedList<Integer> validMoves = new LinkedList<>();
        validMoves.add(1);
        validMoves.add(3);
        validMoves.add(5);
        validMoves.add(7);

        IntStream.rangeClosed(1,10).forEach( i ->
            assertTrue(validMoves.contains(game.randomValidMove(validMoves)))
        );

    }

    @Test
    public void testHasWonWithMove() {
        Game game = TestConfig.buildGameWithBoard(tmpDir, playerTwoWinNextMove());
        game.who(game.player2());
        assertTrue(game.hasWon(2));
    }

 
    @Test
    public void testHasntWonWithMove() {
        Game game = TestConfig.buildGameWithBoard(tmpDir, playerTwoWinNextMove());
        assertFalse(game.hasWon(2));
    }

    @Disabled
    @Test
    public void testIfNoOneCanWinSuggestValidMove() {
        Queue<Integer> validMoves = game.board().getValidMoves();
        Integer suggestedMove = game.suggestMove(game.player1());
        assertTrue(validMoves.contains(suggestedMove));
    }

}