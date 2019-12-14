package com.game.connect4;

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

}