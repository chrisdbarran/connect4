package com.game.connect4;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Board {

    private static final int ROWS = 6;

    private static final int COLUMNS = 7;

    int[][] board = new int[ROWS][COLUMNS];

}