package com.game.connect4;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
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
    public void testAreConsecutive() {
        StreamNeighbours<Integer> cellStream = new StreamNeighbours<>(4);

        cellStream.addNext(new Integer(2));
        cellStream.addNext(new Integer(3));
        cellStream.addNext(new Integer(4));
        cellStream.addNext(new Integer(5));

        assertTrue(Board.areFourIntegersConsecutive(cellStream));
    }


    @Test
    public void testContainsWinningRow() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(1,2,1));
        cells.add(new Cell(1,3,1));
        cells.add(new Cell(1,4,1));
        cells.add(new Cell(1,5,1));
        cells.add(new Cell(1,7,1));
        assertTrue(Board.containsWinningRow(cells));
    }

    @Test
    public void testContainsWinningColumn() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(2,3,1));
        cells.add(new Cell(3,3,1));
        cells.add(new Cell(4,3,1));
        cells.add(new Cell(5,3,1));
        cells.add(new Cell(7,3,1));
        assertTrue(Board.containsWinningColumn(cells));
    }

    @Test
    public void testPlayerWonByRow() throws Exception {
        Board testBoard = playerOneRowWinBoard();
        assertTrue(testBoard.hasWonByRow(1));
    }

    @Test
    public void testPlayerWonByColumn() {
        Board testBoard = playerOneColumnWinBoard();
        assertTrue(testBoard.hasWonByColumn(1));
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

    private Board playerOneRowWinBoard() {
        return new Board(new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,1,1,1,1,0,0},
            {2,1,1,1,2,1,2}
        });
    }

    private Board playerOneColumnWinBoard() {
        return new Board(new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,0,0,0},
            {0,1,2,1,1,0,0},
            {2,1,1,2,2,1,2}
        });
    }

}