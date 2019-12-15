package com.game.connect4;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class BoardTest extends BaseTest {

    @Test
    public void emptyBoardAnyMoveIsValid() {
        Board board = new Board();
        Integer[] sourceArray = {0,1,2,3,4,5,6};
        Set<Integer> expectedMoves = new LinkedHashSet<Integer>(Arrays.asList(sourceArray));
        assertEquals(expectedMoves,board.getValidMoves());
    }

    @Test
    public void fullBoardNoMovesAreValid() throws Exception {
        copyTestFileToTempFolder("testFullBoard.json");
        game.loadGame("testFullBoard.json");
        assertTrue(game.board().getValidMoves().isEmpty());
    }
}