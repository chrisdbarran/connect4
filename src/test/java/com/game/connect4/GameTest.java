package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void newGame() {
        assertNotNull(new Game(),"Game is null");
    }

    @Test
    void newGameWithPlayers() {
        String player1 = "player1";
        String player2 = "player2";
        Game game = new Game(player1, player2);
        assertNotNull(game, "Game should initialise with players");
        assertEquals(player1, game.getPlayer1());
        assertEquals(player2, game.getPlayer2());
    }

}