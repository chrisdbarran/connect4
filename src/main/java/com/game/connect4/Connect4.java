package com.game.connect4;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import com.game.connect4.persistence.FileRepository;
import com.game.connect4.persistence.GameRepository;

import org.springframework.util.StringUtils;

public class Connect4 {

    final Scanner inputScanner;
    final PrintWriter consoleWriter;
    final GameRepository gameRepository;


    private static final List<Integer> numberOfPlayersChoices = Arrays.asList(0,1,2);

    public Connect4(File saveDir, Scanner inputScanner, PrintWriter consoleWriter) {
        this.inputScanner = inputScanner;
        inputScanner.useDelimiter(System.lineSeparator());
        this.consoleWriter = consoleWriter;
        this.gameRepository = new FileRepository(saveDir);

    }

    public static void main(String[] args) throws IOException {
        Console console = System.console();
        Scanner inputScanner = new Scanner(System.in);

        Connect4 connect4 = new Connect4(new File(args[0]), inputScanner, console.writer());
        connect4.run();
    }

    public void run() throws IOException {
        if (willPlay()) {
            boolean gameIsWon = false;
            Integer numberOfPlayers = getNumberOfPlayers(numberOfPlayersChoices);
            Board board = newGame(numberOfPlayers);
            gameRepository.saveGame(board, "connect4.json");
            
            do {
                writeToConsole(BoardPrinter.renderBoard(board));
                writeToConsole(System.lineSeparator());

                if (board.getWho().isHuman()) {
                    Integer move = getMove(board.getValidMoves(), board.getWho());
                    gameIsWon = board.hasWon(move);
                } else {
                    //Integer move = board.suggestMove(board.getWho());
                    Integer move = board.getBestMove(board.getWho());
                    gameIsWon = board.hasWon(move);
                }

            } while (gameNotWonAndThereAreValidMoves(gameIsWon, board));

            writeToConsole(BoardPrinter.renderBoard(board));

            if(gameIsWon) {
                printWinningMessage(board.getWho());
            } else {
                writeToConsole("No more valid moves, game is a tie!");
            }

        }

    }

    public static boolean gameNotWonAndThereAreValidMoves(boolean gameIsWon, Board board) {
        return !gameIsWon && !board.getValidMoves().isEmpty();
    }

    public Integer getMove(Collection<Integer> moves, Player player) {
        String prompt = String.format("%n%s (player %d) take a move : ", player.getName(), player.getPlayerId().value);
        return selectIntegerFromList(moves, prompt);
    }

    public Integer getNumberOfPlayers(Collection<Integer> choices) {
        String prompt = "Select number of players 0, 1 or 2 : ";
        return selectIntegerFromList(choices, prompt);
    }


    public Board newGame(Integer numberOfPlayers) {

        Player player1 = null;
        Player player2 = null;

        switch (numberOfPlayers) {
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
        default:
            throw new IllegalArgumentException("numberOfPlayers : " + numberOfPlayers + " is not a valid choice");
        }
        return new Board(player1, player2);
    }

    public Player getComputerOpponent(int playerId) {
        return new Player("Computer " + playerId, playerId, Player.PlayerType.COMPUTER);
    }

    public Player getPlayer(int playerId) {
        boolean validChoice = false;
        String choice = null;
        do {
            writeToConsole(String.format("Player %s enter name: ", playerId));
                if(inputScanner.hasNext("[\\w\\s]+"))
                {
                    String input = inputScanner.next("[\\w\\s]+");
                    input = input.trim();
                    if(!StringUtils.isEmpty(input)) {
                        validChoice = true;
                        choice = StringUtils.capitalize(input);
                    }
            
                } else {
                    inputScanner.next();
                }
        } while(!validChoice);
        return new Player(choice, playerId, Player.PlayerType.HUMAN);
    }

    public Integer selectIntegerFromList(Collection<Integer> choices, String prompt) {
        boolean validChoice = false;
        Integer choice = null;
        do {
            writeToConsole(prompt);

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
        writeToConsole("Welcome to Connect4, shall we play a game y/N ?");
        String play = inputScanner.next();
        return play.equals("y");
    }

    public void printWinningMessage(Player player) {
        writeToConsole(String.format("%nCongratulations %s, you have won the game.%n", player.getName()));
    }

    public void writeToConsole(String content) {
        consoleWriter.print(content);
        consoleWriter.flush();
    }

}
