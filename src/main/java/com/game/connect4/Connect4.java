package com.game.connect4;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
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
		if(willPlay())
		{
			boolean gameIsWon = false;
			Game game = setupGame();
			do {
				out.print(BoardPrinter.renderBoard(game.board()));
				Integer move = getMove(game.board().getValidMoves(), game.who());
				gameIsWon = game.hasWon(move);

			} while (!gameIsWon);

			out.print(BoardPrinter.renderBoard(game.board()));
			printWinningMessage(game.who());
		}
		
	}

	public Integer getMove(Collection<Integer> moves, Player player) {
		
		boolean validMove = false;
		Integer move = new Integer(0);

		do {
			out.printf("\n%s (player %d) take a move : ", player.getName(), player.getPlayerId().value);
			String input = inputScanner.next();

			try {
				move = Integer.parseInt(input);
				validMove = moves.contains(move);
			} catch (NumberFormatException e)
			{
				validMove = false;
			}

		} while (!validMove);

		return move;
	}

	public Game setupGame() throws Exception {
		Game game = new Game(new File("/tmp/"), getGameData());
		game.saveGame("connect4.json");	
		return game;	
	}

	public GameData getGameData() {
		
		Player player1 = getPlayer(1);
		Player player2 = getPlayer(2);
		return new GameData(player1, player2, new Board());
	}

	public Player getPlayer(int playerId)
	{
		String name = "";
		do {
			out.printf("Player %s enter name: ", playerId);
			while(!inputScanner.hasNext()) {
				out.print("Enter a valid name!");
				inputScanner.next(); // Skip to next token
			}
			name =  StringUtils.capitalize(inputScanner.next());

		} while (name.isEmpty());
		return new Player(name, playerId);
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
