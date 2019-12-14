package com.game.connect4;

import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected static final String PLAYER1 = "player1";

    protected static final String PLAYER2 = "player2";

    protected Game game;

    @BeforeEach
    void setup() {
        game = new Game(PLAYER1, PLAYER2);
    }
}