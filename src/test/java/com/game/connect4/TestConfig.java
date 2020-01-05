package com.game.connect4;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public final class TestConfig {

    public static final String SRC_TEST_RESOURCES = "src/test/resources/";

    public static final Player PLAYER1 = Player.player1("player1");

    public static final Player PLAYER2 = Player.player2("player2");

    public static Board buildDefaultBoard() {
        return new Board(PLAYER1, PLAYER2);
    }

    public static Board buildBoardWithBoard(Board board) {
        return new Board(board);
    }

    public static void copyTestFileToTempFolder(File tmpDir, String filename) throws Exception {
        final Path original = Paths.get(SRC_TEST_RESOURCES + filename);
        final File saveGame = new File(tmpDir, filename);
        Path copied = saveGame.toPath();
        Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);
    }
    

    public static String loadTestFile(String filename) throws Exception {
        return new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES + filename)));
    }

    public static File getTestSaveGame(File tmpDir, String filename) throws Exception {
        return new File(tmpDir,filename);
    }

    public static Board buildGameWithBoardPlayerOneWinNextMove() {
        return playerOneWinNextMove();
    }

    private static Board playerOneWinNextMove() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,1,0,0},
            {0,0,2,1,2,0,0},
            {0,1,2,2,1,0,0},
            {2,1,1,2,2,1,2}
        });
    }

    public static Board buildBoardWithPlayerTwoWinNextMove() {
        return buildBoardWithBoard(playerTwoWinNextMove());
    }

    private static Board playerTwoWinNextMove() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,2,0,0,0},
            {0,0,0,1,0,0,0},
            {0,0,0,1,1,0,0},
            {0,2,2,1,2,0,0},
            {0,1,2,2,1,0,0},
            {2,1,1,1,2,2,2}
        });
    }

    public static Board buildBoardWithBestMove() {
        return buildBoardWithBoard(playerOneBestMove());
    }

    private static Board playerOneBestMove() {
        return new Board(TestConfig.PLAYER1, TestConfig.PLAYER2,new int[][] {
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {2,2,0,1,1,0,0}
        });
    }

}