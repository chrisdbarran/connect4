package com.game.connect4;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

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
            return winningMoves.element();
        }

        // If no winning moves for player 1 look to block winning move for player 2
        Queue<Integer> winningMovesOpponent = board().playerCanWinNextMove(getOpponent(player));
        if(!winningMovesOpponent.isEmpty()) {
            return winningMovesOpponent.element();
        }
        // Otherwise just return a valid move
        return randomValidMove(board().getValidMoves());
    }

    public Integer randomValidMove(Queue<Integer> validMoves) {
        Integer[] moves = new Integer[validMoves.size()];
        moves = validMoves.toArray(moves);
        Random random = new Random();
        int choice = random.nextInt(moves.length - 1);
        return moves[choice];
    }

    public Player getOpponent(Player player)
    {
        if(player.isPlayer1())
        {
            return player2();
        }
        return player1();
    }

    public boolean hasWon(Integer move)
    {
        Player player = who();
        board().makeMove(move, player);
        if(board().hasWon(player)) {
            return true;
        } 
        who(getOpponent(player));
        return false;
    }

}