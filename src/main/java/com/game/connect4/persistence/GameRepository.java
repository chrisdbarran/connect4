package com.game.connect4.persistence;

import java.io.IOException;

import com.game.connect4.GameData;

public interface GameRepository {

    public void saveGame(GameData gameData, String saveGameName) throws IOException;

    public GameData loadGame(String saveGameName) throws IOException;
}