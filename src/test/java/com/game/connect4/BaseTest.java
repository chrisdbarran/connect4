package com.game.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


public class BaseTest {

    private static final String SRC_TEST_RESOURCES = "src/test/resources/";

    private static final String PLAYER1 = "player1";

    private static final String PLAYER2 = "player2";

    protected Game game;

    @TempDir
    static File saveDir;

    @BeforeEach
    void setup() {
        GameData gameData = new GameData(PLAYER1, PLAYER2, new Board());
        game = new Game(saveDir, gameData);
    }

    static Game setupGame(Board board) {
        GameData gameData = new GameData(PLAYER1, PLAYER2, board);
        return new Game(saveDir, gameData);
    }

    static String loadTestFile(String filename) throws Exception {
        return new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES + filename)));
    }

    static List<String> loadTestFileLines(String filename) throws Exception {
        return Files.readAllLines(Paths.get(SRC_TEST_RESOURCES + filename));
    }

    static void copyTestFileToTempFolder(String filename) throws Exception {
        final Path original = Paths.get(SRC_TEST_RESOURCES + filename);
        final File saveGame = new File(saveDir, filename);
        Path copied = saveGame.toPath();
        Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);
    }
}