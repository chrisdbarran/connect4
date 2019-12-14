package com.game.connect4;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class GameData {

    private static final int PLAYER1 = 1;
    
    @NonNull String player1;

    @NonNull String player2;

    int who = PLAYER1;

    Board board = new Board();

}