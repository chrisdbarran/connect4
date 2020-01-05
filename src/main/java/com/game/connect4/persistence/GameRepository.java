package com.game.connect4.persistence;

import java.io.IOException;

import com.game.connect4.Board;

public interface GameRepository {

    public void saveGame(Board board, String saveGameName) throws IOException;

    public Board loadGame(String saveGameName) throws IOException;
}