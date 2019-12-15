package com.game.connect4;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Board {

    static final int ROWS = 6;

    static final int COLUMNS = 7;

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
    
}