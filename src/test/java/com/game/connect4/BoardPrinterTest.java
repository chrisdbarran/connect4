package com.game.connect4;

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

    @BeforeAll
    static void setupAll() throws Exception {
        testBoard = new String(Files.readAllBytes(Paths.get("src/test/resources/testEmptyBoard.txt")));
    }

    @Test
    void printBoard(@Mock PrintStream out) {
        BoardPrinter printer = new BoardPrinter(out);
        printer.printBoard(game.board());
        verify(out).print(testBoard);
    }
    
}