package com.game.connect4;

import java.security.SecureRandom;
import java.util.Queue;
import java.util.Random;

public class Game {

    private GameData gameData;

    private final Random random;

    public Game(GameData gameData)
    {
        this.gameData = gameData;
        random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
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