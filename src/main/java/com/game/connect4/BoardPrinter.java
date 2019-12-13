package com.game.connect4;

import java.io.PrintStream;
import java.util.Arrays;

public class BoardPrinter {

    private PrintStream out;

    public BoardPrinter(PrintStream out) {
        this.out = out;
    }

    public void printBoard(int[][] board) {
        String [] boardArray = Arrays.stream(board).map(BoardPrinter::rowToString)
                        .toArray(String[]::new);

        out.print(String.join("\n", boardArray));
    }

    public static String rowToString(int[] row) {
        String[] stringArray =  Arrays.stream(row)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);

        return String.join(",", stringArray);
    }
}