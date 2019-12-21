package com.game.connect4;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.game.connect4.stream.StreamNeighbours;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class BoardTest extends BaseTest {

    @Test
    public void emptyBoardAnyMoveIsValid() {
        Board board = new Board();
        Integer[] sourceArray = {1,2,3,4,5,6,7};
        Set<Integer> expectedMoves = new LinkedHashSet<Integer>(Arrays.asList(sourceArray));
        assertEquals(expectedMoves,board.getValidMoves());
    }

    @Test
    public void fullBoardNoMovesAreValid() throws Exception {
        copyTestFileToTempFolder("testFullBoard.json");
        game.loadGame("testFullBoard.json");
        assertTrue(game.board().getValidMoves().isEmpty());
    }

    @Test
    public void fullColumnShouldNotBeValidMove() throws Exception {
        Integer[] sourceArray = {1,2,3,5,6,7};
        Set<Integer> expectedMoves = new LinkedHashSet<Integer>(Arrays.asList(sourceArray));
        assertEquals(expectedMoves, testBoard().getValidMoves());
    }

    @Test
    public void makeMoveShouldUpdateBoard() throws Exception {
        Board board = testBoard();
        board.makeMove(7, 2);
        assertEquals(postMoveBoard(), board);
    }

    @Test
    public void detectPlayer1HasWonHorizontalLine() throws Exception {
        Board testBoard = playerOneHorizontalWinBoard();
        assertTrue(testBoard.hasWon(1));
    }

    private Board testBoard() {
        return new Board(new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,1,0,0},
            {2,2,1,1,2,1,0}
        });
    }

    private Board postMoveBoard() {
        return new Board(new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,1,0,0},
            {2,2,1,1,2,1,2}
        });
    }

    private Board playerOneHorizontalWinBoard() {
        return new Board(new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,1,1,1,1,0,0},
            {2,1,1,1,2,1,2}
        });
    }

    @Test
    public void testAreConsecutive() {
        StreamNeighbours<Cell> cellStream = new StreamNeighbours<>(4);

        cellStream.addNext(new Cell(1,2,1));
        cellStream.addNext(new Cell(1,3,1));
        cellStream.addNext(new Cell(1,4,1));
        cellStream.addNext(new Cell(1,5,1));

        assertTrue(Board.areFourCellsConsecutive(cellStream));
    }
}