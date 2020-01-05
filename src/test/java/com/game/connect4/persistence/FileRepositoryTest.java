package com.game.connect4.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import com.game.connect4.Board;
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

        Board board = new Board(TestConfig.PLAYER1, TestConfig.PLAYER2);
        gameRepository.saveGame(board,"gameData.json");
        
        File saveGame = TestConfig.getTestSaveGame(tmpDir, "gameData.json");
        
        assertAll(
            () -> assertTrue(saveGame.exists()),
            () -> assertTrue(saveGame.length() > 0));
    }


    @Test
    void loadGameReadsGameFromFile() throws Exception {
        
        TestConfig.copyTestFileToTempFolder(tmpDir, "testLoadGame.json");
        
        Board board = gameRepository.loadGame("testLoadGame.json");
        
        assertAll(
            () -> assertEquals("Stefan",board.getPlayer1().getName()),
            () -> assertEquals("Mary", board.getPlayer2().getName()),
            () -> assertEquals(board.getPlayer2().getPlayerId(), board.getWho().getPlayerId()),
            () -> assertEquals(testBoard().getCells(), board.getCells()));
    }


    private Board testBoard() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,2,1,0,0},
            {2,2,1,1,2,1,0}
        });
    }
    
}