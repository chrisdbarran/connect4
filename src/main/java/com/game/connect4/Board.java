package com.game.connect4;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Board {

    public Board(int[][] board)
    {
        this.board = board;
    }

    private static final int ROWS = 6;

    private static final int COLUMNS = 7;

    private int[][] board = new int[ROWS][COLUMNS];

    protected Set<Integer> getValidMoves(){
        return getValidMoves1();
    }

    /**
     * Feels like a pretty dumb, verbose approach
     * @return
     */
    protected Set<Integer> getValidMoves1() {
        Set<Integer> validMoves = new LinkedHashSet<Integer>();

        for (int row = board.length-1; row >=0; row--)
        {
            for(int col = 0; col < board[row].length; col++)
            {
                int cell = board[row][col];
                if(cell == 0)
                {
                    validMoves.add(col);
                }
            }
        }
        return validMoves;
    }

    protected Set<Integer> getValidMovesInRow(int[] row) {
        return Arrays.stream(row)
                .filter(cell -> 0 == cell)
                .boxed()
                .collect(Collectors.toSet());
    }
}