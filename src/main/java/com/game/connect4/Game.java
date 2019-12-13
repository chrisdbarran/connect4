package com.game.connect4;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class Game {

    private static final int ROWS = 6;

    private static final int COLUMNS = 7;

    private static final int PLAYER1 = 1;

    private final @NonNull String player1;

    private final @NonNull String player2;

    private int who = PLAYER1;

    private int[][] board = new int[ROWS][COLUMNS];

}