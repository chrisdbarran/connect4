package com.game.connect4;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.springframework.util.StringUtils;

public class Connect4 {

    final Scanner inputScanner;
    final PrintStream out;

    public Connect4(InputStream in, PrintStream out) {
        this.inputScanner = new Scanner(in);
        this.out = out;
    }

    public static void main(String[] args) throws Exception {
        Connect4 connect4 = new Connect4(System.in, System.out);
        connect4.run();
    }

    public void run() throws Exception {
        if (willPlay()) {
            boolean gameIsWon = false;
            Game game = setupGame();
            do {
                out.print(BoardPrinter.renderBoard(game.board()));
                out.println();
                if (game.who().isHuman()) {
                    Integer move = getMove(game.board().getValidMoves(), game.who());
                    gameIsWon = game.hasWon(move);
                } else {
                    Integer move = game.suggestMove(game.who());
                    gameIsWon = game.hasWon(move);
                }

            } while (!gameIsWon);

            out.print(BoardPrinter.renderBoard(game.board()));
            printWinningMessage(game.who());
        }

    }

    public Integer getMove(Collection<Integer> moves, Player player) {
        String prompt = String.format("\n%s (player %d) take a move : ", player.getName(), player.getPlayerId().value);
        return selectIntegerFromList(moves, prompt);
    }

    public Integer getNumberOfPlayers(Collection<Integer> choices) {
        String prompt = "Select number of players 0, 1 or 2 : ";
        return selectIntegerFromList(choices, prompt);
    }

    public Game setupGame() throws Exception {
        Game game = new Game(new File("/tmp/"), getGameData());
        game.saveGame("connect4.json");
        return game;
    }

    public GameData getGameData() {

        final List<Integer> choices = Arrays.asList(0, 1, 2);
        Integer numPlayers = getNumberOfPlayers(choices);
        Player player1 = null;
        Player player2 = null;

        switch (numPlayers) {
        case 1:
            player1 = getPlayer(1);
            player2 = getComputerOpponent(2);
            break;
        case 2:
            player1 = getPlayer(1);
            player2 = getPlayer(2);
            break;
        case 0:
            player1 = getComputerOpponent(1);
            player2 = getComputerOpponent(2);
            break;
        }
        return new GameData(player1, player2, new Board());
    }

    public Player getComputerOpponent(int playerId) {
        return new Player("Computer " + playerId, playerId, Player.PlayerType.COMPUTER);
    }

    public Player getPlayer(int playerId) {
        String name = "";
        do {
            out.printf("Player %s enter name: ", playerId);
            while (!inputScanner.hasNext()) {
                out.print("Enter a valid name!");
                inputScanner.next(); // Skip to next token
            }
            name = StringUtils.capitalize(inputScanner.next());

        } while (name.isEmpty());
        return new Player(name, playerId, Player.PlayerType.HUMAN);
    }

    public Integer selectIntegerFromList(Collection<Integer> choices, String prompt) {
        boolean validChoice = false;
        Integer choice = null;
        do {
            out.print(prompt);

            String input = inputScanner.next();
            try {
                choice = Integer.parseInt(input);
                validChoice = choices.contains(choice);
            } catch (NumberFormatException e) {
                validChoice = false;
            }
        } while (!validChoice);
        return choice;
    }

    public boolean willPlay() {
        out.print("Welcome to Connect4, shall we play a game y/N ?");
        String play = inputScanner.next();
        return play.equals("y");
    }

    public void printWinningMessage(Player player) {
        out.printf("\nCongratulations %s, you have won the game.\n", player.getName());
    }

}
