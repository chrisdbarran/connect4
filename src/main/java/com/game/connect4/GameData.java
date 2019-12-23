package com.game.connect4;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class GameData {
    
    @NonNull Player player1;

    @NonNull Player player2;

    @Setter
    @NonNull Player who;

    @NonNull Board board = new Board();

    public GameData(Player player1, Player player2, Board board)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.who = player1;
        this.board = board;
    }

}