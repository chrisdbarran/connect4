package com.game.connect4;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.game.connect4.Player.PlayerType;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import org.junit.jupiter.api.Test;

@RunWith(JUnitPlatform.class)
public class PlayerTest {

    @Test
    public void testIsPlayer1() {
        Player player1 = Player.player1("player1");
        Player player2 = Player.player2("player2");

        assertAll(
            () -> assertTrue(player1.isPlayer1()),
            () -> assertFalse(player2.isPlayer1()));
    }

    @Test
    public void testIsHuman() {
        Player human = new Player("human", 1, PlayerType.HUMAN);
        assertTrue(human.isHuman());

        Player computer = new Player("Computer", 2, PlayerType.COMPUTER);
        assertFalse(computer.isHuman());
    }
}