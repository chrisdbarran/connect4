package com.game.connect4;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@EqualsAndHashCode
public class Board {

    private static final int ROWS = 6;

    private static final int COLUMNS = 7;

    private int[][] board = new int[ROWS][COLUMNS];

}