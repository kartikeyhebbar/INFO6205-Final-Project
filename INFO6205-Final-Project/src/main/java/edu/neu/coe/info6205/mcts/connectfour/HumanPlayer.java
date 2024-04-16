package edu.neu.coe.info6205.mcts.connectfour;

import edu.neu.coe.info6205.mcts.utils.Board;
import edu.neu.coe.info6205.mcts.utils.Player;

import java.awt.*;
import java.util.Scanner;

public class HumanPlayer implements Player {

    private Scanner scanner = new Scanner(System.in);

    private int playerId; // should be 1 for player 1 or 2 for player 2

    public HumanPlayer(int playerId) {
        this.playerId = playerId;
    }

    public Board makeNextMove(Board board) {
        int move = scanner.nextInt();
        boolean success = board.setSquareOnBoard(new Point(move/10, move % 10), playerId);
        while (!success) {
            System.err.println("Illegal move, try again");
            move = scanner.nextInt();
            success = board.setSquareOnBoard(new Point(move/10, move % 10), playerId);
        }
        return board;
    }
}