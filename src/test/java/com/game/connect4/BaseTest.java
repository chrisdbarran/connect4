package com.game.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class BaseTest {

    protected static final String PLAYER1 = "player1";

    protected static final String PLAYER2 = "player2";

    private static final String SRC_TEST_RESOURCES = "src/test/resources/";

    protected Game game;

    @TempDir
    Path saveDir;

    @BeforeEach
    void setup() {
        game = new Game(PLAYER1, PLAYER2, saveDir);
    }

    static String loadTestFile(String filename) throws Exception {
        return new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES + filename)));
    }

    static List<String> loadTestFileLines(String filename) throws Exception {
        return Files.readAllLines(Paths.get(SRC_TEST_RESOURCES + filename));
    }
}