package com.game.connect4;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    private static String testBoard;
    private static String formattedBoard;

    @BeforeAll
    static void setupAll() throws Exception {
        testBoard = new String(Files.readAllBytes(Paths.get("src/test/resources/testEmptyBoard.txt")));
        formattedBoard = new String(Files.readAllBytes(Paths.get("src/test/resources/testPrintFormattedBoard.txt")));
    }

    @Test
    void printBoard(@Mock PrintStream out) {
        BoardPrinter printer = new BoardPrinter(out);
        printer.printBoard(game.board());
        verify(out).print(testBoard);
    }

    @Test
    void printFormattedBoard(@Mock PrintStream out ) {
        BoardPrinter printer = new BoardPrinter(out);
        printer.printFormattedBoard(game.board());
        verify(out).print(formattedBoard);
    }

    @Test
    void generateBoardHeader() {
       String header =  "|1|2|3|4|5|6|7|\n+-+-+-+-+-+-+-+";
       assertEquals(header, BoardPrinter.renderHeader());
    }
    
}