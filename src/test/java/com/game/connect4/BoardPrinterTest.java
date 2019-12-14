package com.game.connect4;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class BoardPrinterTest extends BaseTest {


    private static String renderedBoard;
    private static String renderedEmptyBoard;

    @BeforeAll
    static void setupAll() throws Exception {
        renderedBoard = loadTestFile("testRenderedBoard.txt");
        renderedEmptyBoard = loadTestFile("testRenderedEmptyBoard.txt");

    }

    @Ignore
    void printFormattedBoard(@Mock PrintStream out ) {
        BoardPrinter printer = new BoardPrinter(out);
        printer.printFormattedBoard(game.board());
        verify(out).print(renderedBoard);
    }

    @Test
    void printFormattedEmptyBoard(@Mock PrintStream out ) {
        BoardPrinter printer = new BoardPrinter(out);
        printer.printFormattedBoard(game.board());
        verify(out).print(renderedEmptyBoard);
    }

    @Test
    void generateBoardHeader() {
       String header =  "|1|2|3|4|5|6|7|\n+-+-+-+-+-+-+-+";
       assertEquals(header, BoardPrinter.renderHeader());
    }
    
}