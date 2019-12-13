package com.game.connect4;

public class Game {

    String player1;

    String player2;

    public Game() {
        
    }

    public Game(String player1, String player2)
    {
        this.player1 = player1;
        this.player2 = player2;
    }

    public String getPlayer1(){
        return this.player1;
    }

    public String getPlayer2(){
        return this.player2;
    }
}