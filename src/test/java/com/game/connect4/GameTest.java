package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class GameTest extends BaseTest {

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
        Board emptyBoard = new Board();
        assertEquals(emptyBoard, game.board());
    } 
}