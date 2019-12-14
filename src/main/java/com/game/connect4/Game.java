package com.game.connect4;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class Game {

    private static final int PLAYER1 = 1;

    private final @NonNull String player1;

    private final @NonNull String player2;

    private int who = PLAYER1;

    private Board board = new Board();

}