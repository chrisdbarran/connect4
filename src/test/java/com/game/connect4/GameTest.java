package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void loadGame() {
        assertNotNull(new Game(),"Game is null");
    }

}