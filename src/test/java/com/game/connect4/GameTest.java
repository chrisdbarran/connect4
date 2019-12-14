package com.game.connect4;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class GameTest extends BaseTest {

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
        Board emptyBoard = new Board();
        assertEquals(emptyBoard, game.board());
    } 

    @Test
    void saveGameWritesGameToFile() throws Exception {
        Path saveGame = saveDir.resolve("game.save");
        
        List<String> testFile = loadTestFileLines("testSaveGame.save");
        
        game.saveGame();

        assertAll(
            () -> assertTrue("File should exist", Files.exists(saveGame)),
            () -> assertLinesMatch(testFile, Files.readAllLines(saveGame)));

    }
}