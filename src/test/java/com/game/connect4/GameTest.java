package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

    private static final String PLAYER1 = "player1";

    private static final String PLAYER2 = "player2";

    private Game game;

    @BeforeEach
    void setup() {
        game = new Game(PLAYER1, PLAYER2);
    }

    @Test
    void newGameWithPlayers() {
        assertNotNull(game, "Game should initialise with players");
        assertEquals(PLAYER1, game.player1());
        assertEquals(PLAYER2, game.player2());
    }

    @Test
    void newGameStartsWithPlayer1() {
        assertEquals(1,game.who(), "Game should start with player1");
    }

    @Test
    void newGameStartsWithEmptyBoard() {
        // Arrays won't match need to use Arrays.equals()
        // However this doesn't work for arrays of arrays
        // So use deepEquals
        int[][] emptyBoard = new int[6][7];
        assertTrue(Arrays.deepEquals(emptyBoard, game.board()));
    }




}