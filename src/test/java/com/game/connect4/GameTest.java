package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
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
        game = TestConfig.buildDefaultGame();
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
    public void suggestMovePlayerOneWin()
    {
        Game game = TestConfig.buildGameWithBoard(playerOneWinNextMove());
        assertEquals(3, game.suggestMove(game.player1()));
    }

    @Test
    public void suggestMoveBlockPlayerTwoWin()
    {
        Game game = TestConfig.buildGameWithBoard(playerTwoWinNextMove());
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
        Game game = TestConfig.buildGameWithBoard(playerTwoWinNextMove());
        game.who(game.player2());
        assertTrue(game.hasWon(2));
    }

 
    @Test
    public void testHasntWonWithMove() {
        Game game = TestConfig.buildGameWithBoard(playerTwoWinNextMove());
        assertFalse(game.hasWon(2));
    }

    @Test
    public void testIfNoOneCanWinSuggestValidMove() {
        Queue<Integer> validMoves = game.board().getValidMoves();
        Integer suggestedMove = game.suggestMove(game.player1());
        assertTrue(validMoves.contains(suggestedMove));
    }

}