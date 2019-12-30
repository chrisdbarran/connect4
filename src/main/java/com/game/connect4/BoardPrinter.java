package com.game.connect4;

import java.io.PrintStream;
import java.util.stream.Collectors;

public class BoardPrinter {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String BOARD_DELIMETER = "|";

    private PrintStream out;

    public BoardPrinter(PrintStream out) {
        this.out = out;
    }


    public void printFormattedBoard(Board board) {    
        out.print(renderBoard(board));
    }

    public static String renderHeader() {
        return String.join(NEW_LINE, "|1|2|3|4|5|6|7|", "+-+-+-+-+-+-+-+");
    }

    public static String renderBoard(Board board) {
        String rows = board.getCells().stream()
                         .map(BoardPrinter::renderCell)
                         .collect(Collectors.joining());

        return String.join(NEW_LINE, BoardPrinter.renderHeader(), rows);
    }

    public static String renderCell(Cell cell) {
        if(cell.isInLastColumn(Board.COLUMNS))
        {
            return BOARD_DELIMETER + cell + BOARD_DELIMETER + NEW_LINE;
        } else {
            return BOARD_DELIMETER + cell;
        }
    }

}