package com.game.connect4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.game.connect4.stream.StreamNeighbours;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode

public class Board  {

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

    public Board(List<Cell> cells)
    {
        this.cells = cells;
    }

    public Board(int[][] boardAsArray)
    {
        this.cells = new ArrayList<Cell>();
        IntStream.rangeClosed(1,ROWS)
                    .forEach(r -> 
                        IntStream.rangeClosed(1, COLUMNS)
                            .forEach(c -> cells.add(new Cell(r,c,boardAsArray[r-1][c-1]))));
    }

    public Board(Board original) {
        // Deep Copy of board
        List<Cell> cells = new ArrayList<Cell>();
        for(Cell cell : original.getCells())
        {
            cells.add(new Cell(cell));
        }
        this.cells = cells;
    }


    Queue<Integer> getValidMoves() {
       
        Set<Integer> validMoves = new LinkedHashSet<Integer>();

        cells.stream()
                .filter(s -> s.isEmpty())
                .peek(s -> validMoves.add(s.getColumn()))
                .count(); // Terminal Operation.

        return new LinkedList<Integer>(validMoves);
    }

    void makeMove(int column, Player player) {
        Cell cell  = cells.stream()
                            .filter(c -> c.getColumn() == column)
                            .filter(c -> c.isEmpty())
                            .max(Comparator.comparing(Cell::getRow))
                            .get();
            cell.setState(player.getPlayerId());
    }

    boolean hasWon(Player player)
    {
        return hasWonByRow(player) || hasWonByColumn(player) || hasWonByDiagonalNorthEast(player) || hasWonByDiagonalSouthEast(player);
    }
    
    boolean hasWonByRow(Player player)
    {
        Map<Integer,List<Cell>> cellMap = groupCellsByPlayer(player, Cell::getRow);
        return playerHasWinningSequence(cellMap, Cell::getColumn);
    }

    boolean hasWonByColumn(Player player)
    {
        Map<Integer,List<Cell>> cellMap = groupCellsByPlayer(player, Cell::getColumn);
        return playerHasWinningSequence(cellMap, Cell::getRow);
    }

    boolean hasWonByDiagonalNorthEast(Player player)
    {
        Map<Integer,List<Cell>> cellMap = groupCellsByPlayer(player, (Cell cell) -> cell.getRow() + cell.getColumn());
        return playerHasWinningSequence(cellMap, Cell::getRow) && playerHasWinningSequence(cellMap, (Cell cell) -> COLUMNS - cell.getColumn());
    }

    boolean hasWonByDiagonalSouthEast(Player player)
    {
        Map<Integer,List<Cell>> cellMap = groupCellsByPlayer(player, (Cell cell) -> cell.getRow() - cell.getColumn());
        return playerHasWinningSequence(cellMap, Cell::getRow) && playerHasWinningSequence(cellMap, Cell::getColumn);
    }

    Map<Integer,List<Cell>> groupCellsByPlayer(Player player, Function<Cell,Integer> groupFunction) {
        
    
        Map<Integer,List<Cell>> cellMap = cells.stream()
               .filter(c -> c.getState().equals(player.getPlayerId()))
               .collect(Collectors.groupingBy(groupFunction::apply));
        return cellMap;
    }

    boolean playerHasWinningSequence(Map<Integer,List<Cell>> cellMap, Function<Cell, Integer> compareFunction) {
             return  cellMap.values().stream()
                    .filter(groupOfCells -> groupOfCells.size() >= WINNING_RUN)
                    .map(groupOfCells -> Board.groupContainsWinningSequence(groupOfCells, compareFunction))
                    .anyMatch(s -> s.equals(true));
    }

    static boolean groupContainsWinningSequence(List<Cell> cells, Function<Cell,Integer> compareFunction) {
        StreamNeighbours<Integer> cellsQueue = new StreamNeighbours<Integer>(WINNING_RUN);

        return cells.stream().map(cell -> cellsQueue.addNext(compareFunction.apply(cell)))
                    .anyMatch(Board::areFourIntegersConsecutive);   
    }


    static boolean areFourIntegersConsecutive(StreamNeighbours<Integer> cellblock) {
      if(cellblock.areFourElementsRead())
      {
        return (cellblock.getLast() - cellblock.getFirst()) + 1 == WINNING_RUN;
      }
      return false;
    }
    
    public Queue<Integer> playerCanWinNextMove(Player player)
    {   
        return getValidMoves().stream()
            .filter(move -> winningMove(player, move))
            .collect(Collectors.toCollection(LinkedList::new));

    }

    private boolean winningMove(Player player, int move) {
        Board futureBoard = new Board(this);
        futureBoard.makeMove(move, player);
        return futureBoard.hasWon(player);
    }


}