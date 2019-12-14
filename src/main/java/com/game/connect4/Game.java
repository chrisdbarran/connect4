package com.game.connect4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class Game {

    private static final int PLAYER1 = 1;

    private final @NonNull String player1;

    private final @NonNull String player2;

    private @NonNull Path saveDir;

    private int who = PLAYER1;

    private Board board = new Board();

    public void saveGame() throws IOException {
        Path saveGame = saveDir.resolve("game.save");
        Files.write(saveGame, renderSaveGame());
    }

    private List<String> renderSaveGame() {
        List<String> saveGame = new ArrayList<String>();

        saveGame.add(this.player1);
        saveGame.add(this.player2);
        saveGame.add(String.valueOf(this.who));
        
        List<String> rows = Arrays.stream(board.board())
                                .map(Game::convertRow)
                                .collect(Collectors.toList());
        saveGame.addAll(rows);

        return saveGame;
    }

    private static String convertRow(int[] row) {

        String[] values = Arrays.stream(row)
                            .mapToObj(String::valueOf)
                            .toArray(String[]::new);

        return String.join(",", values);
    }
}