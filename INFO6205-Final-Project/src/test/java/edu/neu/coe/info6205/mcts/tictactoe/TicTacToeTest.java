package edu.neu.coe.info6205.mcts.tictactoe;

import edu.neu.coe.info6205.mcts.core.State;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TicTacToeTest {

    /**
     *
     */
    @Test
    public void runGame() {
        long seed = 0L;
        TicTacToe target = new TicTacToe(seed); // games run here will all be deterministic.
        State<TicTacToe> state = target.runGame();
        Optional<Integer> winner = state.winner();
        if (winner.isPresent()) assertEquals(Integer.valueOf(TicTacToe.X), winner.get());
        else fail("no winner");
    }

    // MORE TEST CASES

    // This test verifies that players are alternating correctly according to the game rules.
    @Test
    public void runGame_PlayerAlternation() {
        TicTacToe target = new TicTacToe(); // Use non-deterministic or a seed that ensures multiple moves
        State<TicTacToe> initialState = target.start();
        int firstPlayer = initialState.player();

        State<TicTacToe> nextState = initialState.next(initialState.chooseMove(firstPlayer));
        assertNotEquals(firstPlayer, nextState.player());
    }

    // This test verifies that the game correctly identifies terminal states, either by win or draw.
    @Test
    public void runGame_TerminalState() {
        TicTacToe target = new TicTacToe(); // Seed for a quick terminal state
        State<TicTacToe> state = target.runGame();
        assertTrue(state.isTerminal());
    }



}