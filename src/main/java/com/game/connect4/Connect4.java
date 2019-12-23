package com.game.connect4;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Queue;
import java.util.Scanner;

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
			Game game = setupGame();

			while(game.hasValidMoves())
			{
				out.print(BoardPrinter.renderBoard(game.board()));
				Player player = game.who();
				Queue<Integer> moves = game.board().getValidMoves();
				Integer move = getMove(moves, player);
				game.board().makeMove(move, player);
				if(game.board().hasWon(player)) {
					printWinningMessage(player);
					break;
				} else {
					//TODO should make move switch the player?
					game.who(game.getOpponent(player));
				}
			}
		}
		
	}

	public Integer getMove(Collection<Integer> moves, Player player) {
		out.printf("\n%s (player %d) take a move : ", player.getName(), player.getPlayerId().value);
		return inputScanner.nextInt();
	}

	public Game setupGame() throws Exception {
		Game game = new Game(new File("/tmp/"), getPlayers());
		game.saveGame("connect4.json");	
		return game;	
	}

	public GameData getPlayers() {
		out.print("Player 1 enter name: ");
		Player player1 = Player.player1(inputScanner.next());
		out.print("Player 2 enter name: ");
		Player player2 = Player.player2(inputScanner.next());
		return new GameData(player1, player2, new Board());
	}

	public boolean willPlay() {
		printWelcomeMessage();
		String play = inputScanner.next();
		return play.equals("y");
	}

	public void printWelcomeMessage() {
		out.print("Welcome to Connect4, shall we play a game y/N ?");
	}

	public void printWinningMessage(Player player) {
		out.printf("\nCongratulations %s, you have won the game.\n", player.getName());
	}

}
