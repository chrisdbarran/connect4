package com.game.connect4;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
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

        File saveGame = new File(saveDir,"gameData.json");
        
        List<String> testFile = loadTestFileLines("testSaveGame.json");
        
        game.saveGame(null);
        assertAll(
            () -> assertTrue("File should exist", saveGame.exists()),
            () -> assertLinesMatch(testFile, Files.readAllLines(saveGame.toPath())));
    }

    @Test
    void loadGameReadsGameFromFile() throws Exception {
        
        copyTestFileToTempFolder("testLoadGame.json");
        
        game.loadGame("testLoadGame.json");
        
        assertAll(
            () -> assertEquals("Stefan",game.player1()),
            () -> assertEquals("Mary", game.player2()),
            () -> assertEquals(2, game.who()),
            () -> assertTrue(Arrays.deepEquals(testBoard(), game.board().board))
        );
    }


    private int[][] testBoard() {
        return new int[][] {
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,1,0,0},
            {2,2,1,1,2,1,0}
        };
    }

}