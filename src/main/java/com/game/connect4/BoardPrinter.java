package com.game.connect4;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BoardPrinter {

    private static final String COMMA = ",";
    private static final String NEW_LINE = "\n";
    private static final String BOARD_DELIMETER = "|";
    private static final String[] TOKENS = {" ","X","O"};

    private PrintStream out;

    public BoardPrinter(PrintStream out) {
        this.out = out;
    }

    public void printBoard(Board board) {
        out.print(BoardPrinter.formatBoard(board));
    }

    public void printFormattedBoard(Board board) {
        
        out.print(renderBoard(board));
    }

    public static String formatBoard(Board board) {
        String [] boardArray = Arrays.stream(board.getBoard())
                                    .map(BoardPrinter::rowToString)
                                    .toArray(String[]::new);

        return String.join(NEW_LINE, boardArray);
 }

    public static String rowToString(int[] row) {
        String[] stringArray =  Arrays.stream(row)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
        return String.join(COMMA, stringArray);
    }

    public static String renderHeader() {
        return String.join(NEW_LINE, "|1|2|3|4|5|6|7|", "+-+-+-+-+-+-+-+");
    }

    public static String renderBoard(Board board) {
        String rows = Arrays.stream(board.getBoard())
                                .map(BoardPrinter::renderRow)
                                .collect(Collectors.joining(NEW_LINE));

        return String.join(NEW_LINE, BoardPrinter.renderHeader(), rows);
    }

    public static String renderRow(int[] row) {
        String renderedRow = Arrays.stream(row)
                      .mapToObj(BoardPrinter::renderCell)
                      .collect(Collectors.joining(BOARD_DELIMETER));
        return String.join("", BOARD_DELIMETER,renderedRow,BOARD_DELIMETER);
    }

    public static String renderCell(int cell) {
        return TOKENS[cell];
    }


}