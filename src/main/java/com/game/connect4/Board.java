package com.game.connect4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.ToIntFunction;
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
        this.cells = new ArrayList<>();
        
        IntStream.rangeClosed(1, ROWS)
                .forEach(r -> 
                    IntStream.rangeClosed(1,COLUMNS)
                    .forEach(c -> cells.add(new Cell(r,c)))); 
    }

    public Board(int[][] boardAsArray)
    {
        this.cells = new ArrayList<>();
        IntStream.rangeClosed(1,ROWS)
                    .forEach(r -> 
                        IntStream.rangeClosed(1, COLUMNS)
                            .forEach(c -> cells.add(new Cell(r,c,boardAsArray[r-1][c-1]))));
    }

    public Board(Board original) {
        // Deep Copy of board
        List<Cell> copiedCells = new ArrayList<>();
        for(Cell cell : original.getCells())
        {
            copiedCells.add(new Cell(cell));
        }
        this.cells = copiedCells;
    }


    Queue<Integer> getValidMoves() {
       
        Set<Integer> validMoves = new LinkedHashSet<>();
        cells.forEach(
            cell -> {
                if(cell.isEmpty()){
                    validMoves.add(cell.getColumn());
                }
            }
        );
        return new LinkedList<>(validMoves);
    }

    void makeMove(int column, Player player) {
        Optional<Cell> cell  = cells.stream()
                            .filter(c -> c.getColumn() == column)
                            .filter(Board::isCellEmpty)
                            .max(Comparator.comparing(Cell::getRow));

            if(cell.isPresent()) {
                cell.get().setState(player.getPlayerId());
            }
            // cell will always be present, as only valid columns can be chosen.  
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
        return playerHasWinningSequence(cellMap, Cell::getRow);
    }

    boolean hasWonByDiagonalSouthEast(Player player)
    {
        Map<Integer,List<Cell>> cellMap = groupCellsByPlayer(player, (Cell cell) -> cell.getRow() - cell.getColumn());
        return playerHasWinningSequence(cellMap, Cell::getRow);
    }

    Map<Integer,List<Cell>> groupCellsByPlayer(Player player, ToIntFunction<Cell> groupFunction) {
        return cells.stream()
               .filter(c -> c.getState().equals(player.getPlayerId()))
               .collect(Collectors.groupingBy(groupFunction::applyAsInt));
    }

    boolean playerHasWinningSequence(Map<Integer,List<Cell>> cellMap, ToIntFunction<Cell> compareFunction) {
             return  cellMap.values().stream()
                    .filter(groupOfCells -> groupOfCells.size() >= WINNING_RUN)
                    .map(groupOfCells -> Board.groupContainsWinningSequence(groupOfCells, compareFunction))
                    .anyMatch(s -> s.equals(true));
    }

    static boolean groupContainsWinningSequence(List<Cell> cells, ToIntFunction<Cell> compareFunction) {
        StreamNeighbours<Integer> cellsQueue = new StreamNeighbours<>(WINNING_RUN);

        return cells.stream().map(cell -> cellsQueue.addNext(compareFunction.applyAsInt(cell)))
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

    static boolean isCellEmpty(Cell cell) 
    {
        return cell.isEmpty();
    }

}