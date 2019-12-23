package com.game.connect4;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Game {

    private static final String SAVE_GAME_FILENAME = "gameData.json";

    private GameData gameData;
    
    private File saveDir;

    public Game(final File saveDir, GameData gameData)
    {
        this.saveDir = saveDir;
        this.gameData = gameData;
    }

    public void saveGame(final String saveGameName) throws IOException {
        final String filename = Optional.ofNullable(saveGameName).orElse(SAVE_GAME_FILENAME);
        final File saveGame = new File(saveDir, filename);
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(saveGame, gameData);
    }

    public void loadGame(final String saveGameName) throws IOException {

        final String filename = Optional.ofNullable(saveGameName).orElse(SAVE_GAME_FILENAME);
        final File saveGame = new File(saveDir, filename);
        final ObjectMapper objectMapper = new ObjectMapper();
        gameData = objectMapper.readValue(saveGame, GameData.class);
    }

    public int who() {
        return gameData.getWho();
    }

    public Board board() {
        return gameData.getBoard();
    }
    
    public String player1() {
        return gameData.getPlayer1();
    }

    public String player2() {
        return gameData.getPlayer2();
    }

    public int suggestMove(int player) {
        Set<Integer> validMoves = gameData.getBoard().getValidMoves();

        for(int move : validMoves)
        {
            Board futureBoard = gameData.copyOfBoard();
            futureBoard.makeMove(move, player);
            if(futureBoard.hasWon(player))
            {
                return move;
            }
        }
        return -1;
    }
}