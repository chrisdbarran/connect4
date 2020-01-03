package com.game.connect4.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.game.connect4.Board;
import com.game.connect4.GameData;
import com.game.connect4.TestConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileRepositoryTest {

    @TempDir
    File tmpDir;

    GameRepository gameRepository;

    @BeforeEach
    public void setup() {
        gameRepository = new FileRepository(tmpDir);
    }
    

    @Test
    void saveGameWritesGameToFile() throws Exception {

        GameData gameData = new GameData(TestConfig.PLAYER1, TestConfig.PLAYER2, new Board());
        gameRepository.saveGame(gameData,"gameData.json");
        
        File saveGame = TestConfig.getTestSaveGame(tmpDir, "gameData.json");
        
        assertAll(
            () -> assertTrue(saveGame.exists()),
            () -> assertTrue(saveGame.length() > 0));
    }


    @Test
    void loadGameReadsGameFromFile() throws Exception {
        
        TestConfig.copyTestFileToTempFolder(tmpDir, "testLoadGame.json");
        
        GameData gameData = gameRepository.loadGame("testLoadGame.json");
        
        assertAll(
            () -> assertEquals("Stefan",gameData.getPlayer1().getName()),
            () -> assertEquals("Mary", gameData.getPlayer2().getName()),
            () -> assertEquals(gameData.getPlayer2().getPlayerId(), gameData.getWho().getPlayerId()),
            () -> assertEquals(testBoard().getCells(), gameData.getBoard().getCells()));
    }


    private Board testBoard() {
        return new Board(new int[][] {
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,1,0,0},
            {2,2,1,1,2,1,0}
        });
    }
    
}