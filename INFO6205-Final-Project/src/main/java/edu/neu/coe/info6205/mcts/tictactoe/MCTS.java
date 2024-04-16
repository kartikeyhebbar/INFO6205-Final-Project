package edu.neu.coe.info6205.mcts.tictactoe;

import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Class to represent a Monte Carlo Tree Search for TicTacToe.
 */
public class MCTS {
    public static TicTacToeNode root;

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        root = new TicTacToeNode(game.start());
        System.out.println(game.start().toString());
        MCTS mcts = new MCTS(root);
        mcts.run(1000); // Run 1000 iterations of MCTS

        // After running MCTS, you might want to choose the best move
        // This can be done by selecting the child of the root with the highest win ratio
//        Node<TicTacToe> bestMove = root.children().stream()
//                .max(Comparator.comparing(n -> n.wins() / (double) n.playouts()))
//                .orElseThrow();
        if (root.children().isEmpty()) {
            System.out.println("No moves available.");
        } else {
            Node<TicTacToe> bestMove = mcts.bestChild(root);
            if (bestMove != null) {
                System.out.println("Recommended move: " + bestMove.state().toString());
            } else {
                System.out.println("No best move could be determined.");
            }
        }
    }
    public void run(int iterations) {
        for (int i = 0; i < iterations; i++) {
            Node<TicTacToe> node = select(root);
            int result = simulate(node);
            backPropagate(node, result);
        }
    }

    Node<TicTacToe> select(Node<TicTacToe> node) {
//        System.out.println(node.isLeaf());
        while (!node.isLeaf()) {
            if (!node.children().isEmpty()) {
                node = bestChild(node);
            } else {
                node.explore();  // This will handle the expansion and backpropagation
                return node;
            }
        }
        return node;
    }

    Node<TicTacToe> bestChild(Node<TicTacToe> node) {
        if (node.children().isEmpty()) {
            return null; // Or handle differently if needed
        }
        return node.children().stream()
                .max(Comparator.comparingDouble(this::ucb1))
                .orElseThrow(() -> new IllegalStateException("No best child found, but children list is not empty"));
    }

    private double ucb1(Node<TicTacToe> node) {
        double c = Math.sqrt(2);
        return node.wins() / (double) node.playouts() +
                c * Math.sqrt(Math.log(node.getParent().playouts()) / (double) node.playouts());
    }

    int simulate(Node<TicTacToe> node) {
        State<TicTacToe> state = node.state();
        Random random = new Random();
        while (!state.isTerminal()) {
            List<Move<TicTacToe>> moves = new ArrayList<>(state.moves(state.player()));
            Move<TicTacToe> move = moves.get(random.nextInt(moves.size()));
            state = state.next(move);
        }
        return state.winner().orElse(0); // Assuming 0 for draw, 1 for X wins, -1 for O wins.
    }

    void backPropagate(Node<TicTacToe> node, int result) {
        while (node != null) {
            int playout = node.playouts();
            node.setPlayouts(playout+1);
//            node.playouts()++;
            int win = node.wins();
            if ((node.state().player() == 1 && result == 1) ||
                    (node.state().player() == 0 && result == -1)) {
                node.setWins(win + 2);
//                node.wins() += 2;
            } else if (result == 0) {
                node.setWins(win + 1);
//                node.wins() += 1; // Draw is considered 1 point
            }
            node = node.getParent();
        }
    }

    public MCTS(TicTacToeNode root) {
        MCTS.root = root;
    }

//    private final Node<TicTacToe> root;
}