package com.game.connect4;

import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


public final class TestConfig {

    public static final String SRC_TEST_RESOURCES = "src/test/resources/";

    public static final Player PLAYER1 = Player.player1("player1");

    public static final Player PLAYER2 = Player.player2("player2");

    @TempDir
    static File saveDir;

    static Game buildDefaultGame() {
        GameData gameData = new GameData(PLAYER1, PLAYER2, new Board());
        Game game = new Game(saveDir, gameData);
        return game;
    }

    static Game buildGameWithBoard(Board board) {
        GameData gameData = new GameData(PLAYER1, PLAYER2, board);
        return new Game(saveDir, gameData);
    }

    static void copyTestFileToTempFolder(String filename) throws Exception {
        final Path original = Paths.get(SRC_TEST_RESOURCES + filename);
        final File saveGame = new File(saveDir, filename);
        Path copied = saveGame.toPath();
        Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);
    }
    

    static String loadTestFile(String filename) throws Exception {
        return new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES + filename)));
    }

    static List<String> loadTestFileLines(String filename) throws Exception {
        return Files.readAllLines(Paths.get(SRC_TEST_RESOURCES + filename));
    }

    static File getTestSaveGame(String filename) throws Exception {
        return new File(saveDir,filename);
    }

}