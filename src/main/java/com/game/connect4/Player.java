package com.game.connect4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Player {

    public enum PlayerType {HUMAN, COMPUTER}
    private final String name;
    private final CellState playerId;
    private final PlayerType playerType;
    @JsonIgnore
    private static final int PLAYER1 = 1;

    @JsonCreator
    public Player (@JsonProperty("name") String name, @JsonProperty("playerId") int playerId, @JsonProperty("playerType") PlayerType playerType) {
        this.name = name;
        this.playerId = CellState.valueOfPlayer(playerId);
        this.playerType = playerType;
    }

    @JsonIgnore
    public boolean isPlayer1() {
        return playerId.value == PLAYER1;
    }

    @JsonIgnore
    public boolean isHuman() {
        return playerType.equals(PlayerType.HUMAN);
    }

    static Player player1(String name) {
        return new Player(name, CellState.PLAYER1, PlayerType.HUMAN);
    }

    static Player player2(String name) {
        return new Player(name, CellState.PLAYER2, PlayerType.HUMAN);
    }
}