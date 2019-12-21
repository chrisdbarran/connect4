package com.game.connect4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.game.connect4.stream.StreamNeighbours;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Board {

    static final int ROWS = 6;

    static final int COLUMNS = 7;

    static final int WINNING_RUN = 4;

    private List<Cell> cells;

    public Board() {
        this.cells = new ArrayList<Cell>();
        
        IntStream.rangeClosed(1, ROWS)
                .forEach(r -> 
                    IntStream.rangeClosed(1,COLUMNS)
                    .forEach(c -> cells.add(new Cell(r,c)))); 
    }

    public Board(List<Cell> board)
    {
        this.cells = board;
    }

    public Board(int[][] boardAsArray)
    {
        this.cells = new ArrayList<Cell>();
        IntStream.rangeClosed(1,ROWS)
                    .forEach(r -> 
                        IntStream.rangeClosed(1, COLUMNS)
                            .forEach(c -> cells.add(new Cell(r,c,boardAsArray[r-1][c-1]))));
    }


    Set<Integer> getValidMoves() {
       
        Set<Integer> validMoves = new LinkedHashSet<Integer>();

        cells.stream()
                .filter(s -> s.isEmpty())
                .peek(s -> validMoves.add(s.column))
                .count(); // Terminal Operation.

        return validMoves;
    }

    void makeMove(int column, int player) {
        Cell cell  = cells.stream()
                            .filter(c -> c.column == column)
                            .filter(c -> c.isEmpty())
                            .max(Comparator.comparing(Cell::getRow))
                            .get();
            cell.state = CellState.valueOfPlayer(player);
    }

    boolean hasWon(int player)
    {
        return hasWonByRow(player) || hasWonByColumn(player);
    }

    boolean hasWonByRow(int player) {
        return cells.stream()
               .filter(c -> c.state.value == player)
               .collect(Collectors.groupingBy(Cell::getRow)).values().stream()
               .filter(rowOfCells -> rowOfCells.size() >= WINNING_RUN)
               .map(Board::containsWinningRow)
               .anyMatch(s -> s.equals(true));
    }

    boolean hasWonByColumn(int player) {
        return cells.stream()
                .filter(c -> c.state.value == player)
                .collect(Collectors.groupingBy(Cell::getColumn)).values().stream()
                .filter(columnOfCells -> columnOfCells.size() >= WINNING_RUN)
                .map(Board::containsWinningColumn)
                .allMatch(s -> s.equals(true));
    }

    static boolean containsWinningRow(List<Cell> cells) {
        StreamNeighbours<Integer> cellsQueue = new StreamNeighbours<Integer>(WINNING_RUN);
        // Note: if you are testing the row then compare the columns.
        return cells.stream().map(cell -> cellsQueue.addNext(cell.getColumn()))
                    .anyMatch(Board::areFourIntegersConsecutive);   
    }

    static boolean containsWinningColumn(List<Cell> cells) {
        StreamNeighbours<Integer> cellsQueue = new StreamNeighbours<Integer>(WINNING_RUN);
        return cells.stream().map(cell -> cellsQueue.addNext(cell.getRow()))
                    .anyMatch(Board::areFourIntegersConsecutive);
    }

    static boolean areFourIntegersConsecutive(StreamNeighbours<Integer> cellblock) {
      if(cellblock.areFourElementsRead())
      {
        return (cellblock.getLast() - cellblock.getFirst()) + 1 == WINNING_RUN;
      }
      return false;
    }
    
}