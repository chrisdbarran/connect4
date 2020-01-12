package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static com.game.connect4.TestConfig.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import com.game.connect4.persistence.FileRepository;
import com.game.connect4.persistence.GameRepository;
import com.game.connect4.stream.StreamNeighbours;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class BoardTest {

    private Board board;

    private GameRepository gameRepository;

    @TempDir
    File tmpDir;

    @BeforeEach
    void setup() {
        board = new Board(TestConfig.PLAYER1, TestConfig.PLAYER2);
        gameRepository = new FileRepository(tmpDir);
    }

    @Test
    public void emptyBoardAnyMoveIsValid() {
        Board board = new Board(TestConfig.PLAYER1, TestConfig.PLAYER2);
        Integer[] sourceArray = {1,2,3,4,5,6,7};
        Queue<Integer> expectedMoves = new LinkedList<Integer>(Arrays.asList(sourceArray));
        assertEquals(expectedMoves,board.getValidMoves());
    }

    @Test
    public void fullBoardNoMovesAreValid() throws Exception {
        copyTestFileToTempFolder(tmpDir, "testFullBoard.json");
        Board board = gameRepository.loadGame("testFullBoard.json");
        assertTrue(board.getValidMoves().isEmpty());
    }

    @Test
    public void fullColumnShouldNotBeValidMove() throws Exception {
        Integer[] sourceArray = {1,2,3,5,6,7};
        Queue<Integer> expectedMoves = new LinkedList<Integer>(Arrays.asList(sourceArray));
        assertEquals(expectedMoves, testBoard().getValidMoves());
    }

    @Test
    public void makeMoveShouldUpdateBoard() throws Exception {
        Board board = testBoard();
        board.makeMove(7, board.getPlayer2());
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
        assertTrue(Board.groupContainsWinningSequence(cells, Cell::getColumn));
    }

    @Test
    public void testContainsWinningColumn() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(2,3,1));
        cells.add(new Cell(3,3,1));
        cells.add(new Cell(4,3,1));
        cells.add(new Cell(5,3,1));
        cells.add(new Cell(7,3,1));
        assertTrue(Board.groupContainsWinningSequence(cells, Cell::getRow));
    }

    @Test
    public void testContainsWinningDiagonalNorthEast() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(3,5,1));
        cells.add(new Cell(4,4,1));
        cells.add(new Cell(5,3,1));
        cells.add(new Cell(6,2,1));

        boolean winningDiagonal = Board.groupContainsWinningSequence(cells,(Cell cell) -> 7 - cell.getColumn()) && Board.groupContainsWinningSequence(cells, Cell::getRow);
        assertTrue(winningDiagonal);
    }


    @Test
    public void testContainsWinningDiagonalSouthEast() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(3,2,1));
        cells.add(new Cell(4,3,1));
        cells.add(new Cell(5,4,1));
        cells.add(new Cell(6,5,1));

        boolean winningDiagonal = Board.groupContainsWinningSequence(cells,Cell::getColumn) && Board.groupContainsWinningSequence(cells, Cell::getRow);
        assertTrue(winningDiagonal);
    }

    @Test
    public void testPlayerWonByRow() throws Exception {
        Board testBoard = playerOneRowWinBoard();
        assertTrue(testBoard.hasWonByRow(testBoard.getPlayer1()));
    }

    @Test
    public void testPlayerWonByColumn() {
        Board testBoard = playerOneColumnWinBoard();
        assertTrue(testBoard.hasWonByColumn(testBoard.getPlayer1()));
    }

    @Test
    public void testPlayerWonByDiagonalNorthEast() {
        assertAll(
            () -> assertTrue(playerOneDiagonalWinBoardNE().hasWonByDiagonalNorthEast(board.getPlayer1())),
            () -> assertFalse(this.playerOneRowWinBoard().hasWonByDiagonalNorthEast(board.getPlayer1())),
            () -> assertFalse(this.playerOneColumnWinBoard().hasWonByDiagonalNorthEast(board.getPlayer1())),
            () -> assertFalse(this.playerOneNoWinBoardNE().hasWonByDiagonalNorthEast(board.getPlayer1()))
        );
        
    }

    @Test
    public void testPlayerWonByDiagonalSourthEast() {
        Board testBoard = playerOneDiagonalWinBoardSE();
        assertTrue(testBoard.hasWonByDiagonalSouthEast(board.getPlayer1()));
    }

    @Test
    public void testHasWon() {
        assertAll(
            () -> assertTrue(this.playerOneRowWinBoard().hasWon(board.getPlayer1())),
            () -> assertTrue(this.playerOneColumnWinBoard().hasWon(board.getPlayer1())),
            () -> assertTrue(this.playerOneDiagonalWinBoardNE().hasWon(board.getPlayer1())),
            () -> assertTrue(this.playerOneDiagonalWinBoardSE().hasWon(board.getPlayer1()))
        );
    }

    @Test
    public void testModifyCopyDoesntModifyOriginalBoard() {
        Board testBoard = playerTwoWinNextMove();
        Board copyOfBoard = new Board(testBoard);
        copyOfBoard.makeMove(3, board.getPlayer1());
        assertFalse(testBoard.equals(copyOfBoard));
    }

    @Test
    public void testOpponentWinNextMove() {
        Board testBoard = playerTwoWinNextMove();
        Queue<Integer> winningMoves = new LinkedList<>();
        winningMoves.add(2);
        assertEquals(winningMoves, testBoard.playerCanWinNextMove(board.getPlayer2()));
    }


    private Board testBoard() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,1,0,0},
            {2,2,1,1,2,1,0}
        });
    }

    private Board postMoveBoard() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,1,0,0},
            {2,2,1,1,2,1,2}
        });
    }

    private Board playerOneRowWinBoard() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,1,1,1,1,0,0},
            {2,1,1,1,2,1,2}
        });
    }

    private Board playerOneColumnWinBoard() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,0,0,0},
            {0,1,2,1,1,0,0},
            {2,1,1,2,2,1,2}
        });
    }

    private Board playerOneDiagonalWinBoardNE() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,1,0,0},
            {0,0,0,1,2,0,0},
            {0,1,1,2,1,0,0},
            {2,1,1,2,2,1,2}
        });
    }

    private Board playerOneNoWinBoardNE() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,1,0,0},
            {0,0,0,1,2,0,0},
            {0,1,2,2,1,0,0},
            {2,1,1,2,2,1,2}
        });
    }

    private Board playerOneDiagonalWinBoardSE() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,1,1,1,0,0},
            {0,0,2,1,2,0,0},
            {0,1,2,2,1,0,0},
            {2,1,1,2,2,1,2}
        });
    }

    private Board playerTwoWinNextMove() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,1,0,0},
            {0,2,2,1,2,0,0},
            {0,1,2,2,1,0,0},
            {2,1,1,1,2,2,2}
        });
    }

    public static void printBoard(Board board) {
        System.out.println(BoardPrinter.renderBoard(board));
    }

    @Test
    public void bestMoveDetectsWinForPlayer1FirstMove() {
        Board board = TestConfig.buildGameWithBoardPlayerOneWinNextMove();
        assertEquals(3, board.getBestMove(board.getPlayer1()));
    }

    @Test
    public void bestMoveBlockWinForPlayer2FirstMove() {
        Board board = TestConfig.buildBoardWithPlayerTwoWinNextMove();
        assertEquals(2, board.getBestMove(board.getPlayer1()));
    }

    //@Disabled
    @Test
    public void shouldSuggestBestMove() {
        Board board = TestConfig.buildBoardWithBestMove();
        assertEquals(3, board.getBestMove(board.getPlayer2()));
    }

    @Test
    public void testGetOpponent() {
        Player player1 = board.getPlayer1();
        Player player2 = board.getPlayer2();

        assertAll(
            () -> assertEquals(player2, board.getOpponent(player1)),
            () -> assertEquals(player1, board.getOpponent(player2))
        );
    }

    @Test
    public void testGetWho() {
        assertEquals(board.getPlayer1(), board.getWho());
    }

    @Test
    public void testSetWho() {
        Player player2 = board.getPlayer2();
        board.setWho(player2);
        assertEquals(player2, board.getWho());
    }


    @Test
    public void testRandomValidMoveReturnsAValidMove() {
        LinkedList<Integer> validMoves = new LinkedList<>();
        validMoves.add(1);
        validMoves.add(3);
        validMoves.add(5);
        validMoves.add(7);

        IntStream.rangeClosed(1,10).forEach( i ->
            assertTrue(validMoves.contains(board.randomValidMove(validMoves)))
        );

    }

    @Test
    public void testHasWonWithMove() {
        Board board = TestConfig.buildBoardWithPlayerTwoWinNextMove();
        board.setWho(board.getPlayer2());
        assertTrue(board.hasWon(2));
    }

    @Test
    public void testHasntWonWithMove() {
        Board board = TestConfig.buildBoardWithPlayerTwoWinNextMove();
        assertFalse(board.hasWon(2));
    }

    @Test
    public void testIfNoOneCanWinSuggestValidMove() {
        Queue<Integer> validMoves = board.getValidMoves();
        Integer suggestedMove = board.suggestMove(board.getPlayer1());
        assertTrue(validMoves.contains(suggestedMove));
    }


    @Test
    public void suggestMoveBlockPlayerTwoWin()
    {
        Board board = TestConfig.buildBoardWithPlayerTwoWinNextMove();
        assertEquals(2, board.suggestMove(board.getPlayer1()));
    }

    @Test
    public void suggestMovePlayerOneWin()
    {
        Board board = TestConfig.buildGameWithBoardPlayerOneWinNextMove();
        assertEquals(3, board.suggestMove(board.getPlayer1()));
    }
}