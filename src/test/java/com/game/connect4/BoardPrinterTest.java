package com.game.connect4;

import static com.game.connect4.TestConfig.copyTestFileToTempFolder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.PrintStream;

import com.game.connect4.persistence.FileRepository;
import com.game.connect4.persistence.GameRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class BoardPrinterTest {


    private static String renderedBoard;
    private static String renderedEmptyBoard;
    private static final String NEW_LINE = System.lineSeparator();

    @TempDir
    File tmpDir;

    private GameRepository gameRepository;


    @BeforeEach
    void setup() {
        gameRepository = new FileRepository(tmpDir);
    }

    @BeforeAll
    static void setupAll() throws Exception {
        renderedBoard = TestConfig.loadTestFile("testRenderedBoard.txt");
        renderedEmptyBoard = TestConfig.loadTestFile("testRenderedEmptyBoard.txt");

    }

    @Test
    void printFormattedBoard(@Mock PrintStream out ) throws Exception {
        BoardPrinter printer = new BoardPrinter(out);
        copyTestFileToTempFolder(tmpDir,"testLoadGame.json");

        Board board = gameRepository.loadGame("testLoadGame.json");
        printer.printFormattedBoard(board);
        verify(out).print(renderedBoard);
    }

    @Test
    void printFormattedEmptyBoard(@Mock PrintStream out ) {
        BoardPrinter printer = new BoardPrinter(out);
        printer.printFormattedBoard(TestConfig.buildDefaultBoard());
        verify(out).print(renderedEmptyBoard);
    }

    @Test
    void generateBoardHeader() {
       String header =  String.join(NEW_LINE, "|1|2|3|4|5|6|7|","+-+-+-+-+-+-+-+");
       assertEquals(header, BoardPrinter.renderHeader());
    }
    
}