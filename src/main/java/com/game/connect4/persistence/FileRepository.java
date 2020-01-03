package com.game.connect4.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.connect4.GameData;

public class FileRepository implements GameRepository {

    private static final String SAVE_GAME_FILENAME = "gameData.json";
    
    private File saveDir;

    public FileRepository (File saveDir) {
        this.saveDir = saveDir;
    }

    @Override
    public void saveGame(final GameData gameData, final String saveGameName) throws IOException {
        final String filename = Optional.ofNullable(saveGameName).orElse(SAVE_GAME_FILENAME);
        final File saveGame = new File(saveDir, filename);
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(saveGame, gameData);
    }

    @Override
    public GameData loadGame(final String saveGameName) throws IOException {

        final String filename = Optional.ofNullable(saveGameName).orElse(SAVE_GAME_FILENAME);
        final File saveGame = new File(saveDir, filename);
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(saveGame, GameData.class);
    }

}