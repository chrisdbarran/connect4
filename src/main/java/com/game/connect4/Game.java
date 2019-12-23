package com.game.connect4;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Queue;
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

    public Player who() {
        return gameData.getWho();
    }

    public void who(Player player) {
        gameData.setWho(player);
    }

    public Board board() {
        return gameData.getBoard();
    }
    
    public Player player1() {
        return gameData.getPlayer1();
    }

    public Player player2() {
        return gameData.getPlayer2();
    }

    public boolean hasValidMoves() {
        return board().getValidMoves().size() > 0;
    }

    public Integer suggestMove(Player player) {
        Queue<Integer> winningMoves = board().playerCanWinNextMove(player);

        if(!winningMoves.isEmpty()) {
            System.out.println("Player : " + player.getPlayerId() + " Wins : " + BoardPrinter.renderBoard(board()));
            return winningMoves.element();
        }

        // If no winning moves for player 1 look to block winning move for player 2
        Queue<Integer> winningMovesOpponent = board().playerCanWinNextMove(getOpponent(player));
        if(!winningMovesOpponent.isEmpty()) {
            System.out.println("Player : " + player.getPlayerId() + " Blocked : " + BoardPrinter.renderBoard(board()));
            return winningMovesOpponent.element();
        }
        // Otherwise just return a valid move
        return board().getValidMoves().element();
    }

    public Player getOpponent(Player player)
    {
        if(player.isPlayer1())
        {
            return player2();
        }
        return player1();
    }

}