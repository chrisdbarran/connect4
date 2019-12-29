package com.game.connect4;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CellState {

    EMPTY(" ",0),
    PLAYER1("X",1),
    PLAYER2("O",2);

    public final String token;

    @JsonValue
    public final int value;

    private CellState(String token, int value) 
    {
        this.token = token;
        this.value = value;
    }

    public static CellState valueOfPlayer(int i) {
        for (CellState cs : values()) {
            if (cs.value == i)
            {
                return cs;
            }
        }
        return CellState.EMPTY;
    }

    @Override
    public String toString(){
        return this.token;
    }
    
}