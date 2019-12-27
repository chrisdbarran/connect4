package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class CellStateTest {

    @Test
    public void defaultCellStateIsEmpty() {
        assertAll(
            () -> assertEquals(CellState.EMPTY, CellState.valueOfPlayer(0)),
            () -> assertEquals(CellState.EMPTY, CellState.valueOfPlayer(3)));
    }

    @Test
    public void valueOfPlayerReturnsCorrectPlayer() {
        assertAll(
            () -> assertEquals(CellState.PLAYER1, CellState.valueOfPlayer(1)),
            () -> assertEquals(CellState.PLAYER2, CellState.valueOfPlayer(2)));
    }
}