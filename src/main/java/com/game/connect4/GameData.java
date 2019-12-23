package com.game.connect4;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Getter
public class GameData {

    private static final int PLAYER1 = 1;
    
    @NonNull Player player1;

    @NonNull Player player2;

    int who = PLAYER1;

    @NonNull Board board = new Board();

    public GameData(Player player1, Player player2, Board board)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }

}