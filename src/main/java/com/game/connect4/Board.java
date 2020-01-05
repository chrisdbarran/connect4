package com.game.connect4;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
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

    @EqualsAndHashCode.Include
    private List<Cell> cells;

    @EqualsAndHashCode.Include
    private Player player1;

    @EqualsAndHashCode.Include
    private Player player2;

    @EqualsAndHashCode.Include
    private Player who;

    @EqualsAndHashCode.Exclude
    private final Random random;

    public Board() {
        random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
    }

    public Board(Player player1, Player player2) {
        this();
        this.player1 = player1;
        this.player2 = player2;
        this.who = player1;


        
        this.cells = new ArrayList<>();
        
        IntStream.rangeClosed(1, ROWS)
                .forEach(r -> 
                    IntStream.rangeClosed(1,COLUMNS)
                    .forEach(c -> cells.add(new Cell(r,c)))); 
    }

    public Board(Player player1, Player player2, int[][] boardAsArray)
    {
        this(player1, player2);
        this.cells = new ArrayList<>();
        IntStream.rangeClosed(1,ROWS)
                    .forEach(r -> 
                        IntStream.rangeClosed(1, COLUMNS)
                            .forEach(c -> cells.add(new Cell(r,c,boardAsArray[r-1][c-1]))));
    }

    public Board(Board original) {
        this(original.getPlayer1(), original.getPlayer2());
        // Deep Copy of cells
        List<Cell> copiedCells = new ArrayList<>();
        for(Cell cell : original.getCells())
        {
            copiedCells.add(new Cell(cell));
        }
        this.cells = copiedCells;
    }

    public Player who() {
        return this.who;
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

    public int minimax(Player player, boolean maximising) {
        // if there are valid moves
        for(Integer move : this.getValidMoves()) {
            
            // Make a copy of the board
            Board nextBoard = new Board(this);
            // Make the move
            nextBoard.makeMove(move, player);
            if(this.hasWon(player)) {
                return maximising ? 1 : -1;
            }
            return minimax(this.getOpponent(player), !maximising);
        }
  
            
            // if no-one wins call minimax() again
        
        // There are no valid moves return tie
        return 0;
    }

    public Player getOpponent(Player player)
    {
        if(player.isPlayer1())
        {
            return player2;
        }
        return player1;
    }

    public boolean hasWon(Integer move)
    {
        Player player = who;
        makeMove(move, player);
        if(hasWon(player)) {
            return true;
        } 
        setWho(getOpponent(player));
        return false;
    }


    public Integer randomValidMove(Queue<Integer> validMoves) {
        Integer[] moves = new Integer[validMoves.size()];
        moves = validMoves.toArray(moves);
        int choice = random.nextInt(moves.length - 1);
        return moves[choice];
    }


    public Integer suggestMove(Player player) {
        Queue<Integer> winningMoves = playerCanWinNextMove(player);

        if(!winningMoves.isEmpty()) {
            return winningMoves.element();
        }

        // If no winning moves for player 1 look to block winning move for player 2
        Queue<Integer> winningMovesOpponent = playerCanWinNextMove(getOpponent(player));
        if(!winningMovesOpponent.isEmpty()) {
            return winningMovesOpponent.element();
        }
        // Otherwise just return a valid move
        return randomValidMove(getValidMoves());
    }

}