package com.game.connect4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@JsonPropertyOrder({"column","row","state"})
@Getter
@Setter
@EqualsAndHashCode
public class Cell {

    private final int column;
    private final int row;
    private @NonNull CellState state = CellState.EMPTY;



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