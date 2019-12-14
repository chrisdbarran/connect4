package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class GameTest {

    private static final String PLAYER1 = "player1";

    private static final String PLAYER2 = "player2";

    private Game game;

    private static String testBoard;

    @BeforeAll
    static void setupAll() throws Exception {
        testBoard = new String(Files.readAllBytes(Paths.get("src/test/resources/testBoard.txt")));
    }

    @BeforeEach
    void setup() {
        game = new Game(PLAYER1, PLAYER2);
    }

    @Test
    void newGameWithPlayers() {
        assertNotNull(game, "Game should initialise with players");
        assertEquals(PLAYER1, game.player1());
        assertEquals(PLAYER2, game.player2());
    }

    @Test
    void newGameStartsWithPlayer1() {
        assertEquals(1,game.who(), "Game should start with player1");
    }

    @Test
    void newGameStartsWithEmptyBoard() {
        // Arrays won't match need to use Arrays.equals()
        // However this doesn't work for arrays of arrays
        // So use deepEquals
        Board emptyBoard = new Board();
        assertEquals(emptyBoard, game.board());
    }

    @Test
    void printBoard(@Mock PrintStream out) {

        BoardPrinter printer = new BoardPrinter(out);
        printer.printBoard(game.board());
        verify(out).print(testBoard);
    }

}