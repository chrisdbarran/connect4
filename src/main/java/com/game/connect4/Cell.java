package com.game.connect4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@JsonPropertyOrder({"column","row","state"})
@Getter
@EqualsAndHashCode
public class Cell {

    final int column;
    final int row;
    @NonNull CellState state = CellState.EMPTY;



    public Cell (int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    @JsonCreator
    public Cell (@JsonProperty("row") int row, @JsonProperty("column")int column, @JsonProperty("state") int state)
    {
        this.row = row;
        this.column = column;
        this.state = CellState.valueOfPlayer(state);
    }

    boolean isEmpty()
    {
        return state == CellState.EMPTY;
    }

    public String toString()
    {
        return state.toString();
    }

    boolean isInLastColumn(int COLUMNS) {
        return COLUMNS == this.column;
    }
}