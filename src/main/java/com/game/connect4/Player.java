package com.game.connect4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Player {

    private final String name;
    private final CellState playerId;

    @JsonCreator
    public Player (@JsonProperty("name") String name, @JsonProperty("playerId") int playerId) {
        this.name = name;
        this.playerId = CellState.valueOfPlayer(playerId);
    }

    static Player player1(String name) {
        return new Player(name, CellState.PLAYER1);
    }

    static Player player2(String name) {
        return new Player(name, CellState.PLAYER2);
    }
}