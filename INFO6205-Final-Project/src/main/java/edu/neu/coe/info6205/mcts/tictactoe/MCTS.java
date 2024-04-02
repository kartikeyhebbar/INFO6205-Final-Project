package edu.neu.coe.info6205.mcts.tictactoe;

import edu.neu.coe.info6205.mcts.core.Node;

/**
 * Class to represent a Monte Carlo Tree Search for TicTacToe.
 */
public class MCTS {

    public static void main(String[] args) {
        MCTS mcts = new MCTS(new TicTacToeNode(new TicTacToe().new TicTacToeState()));
        Node<TicTacToe> root = mcts.root;

        // This is where you process the MCTS to try to win the game.
        int iterations = 1000;
        mcts.runMCTS(iterations, root);
    }

    // MCTS methods
    public void runMCTS(int iterations, Node<TicTacToe> root) {
        for (int i = 0; i < iterations; i++) {
            // Selection
            TicTacToeNode promisingNode = selectPromisingNode((TicTacToeNode) root);

            // Expansion
            if (!promisingNode.state().isTerminal()) {
                promisingNode.explore();
            }

            // Simulation
            TicTacToeNode nodeToExplore = promisingNode;
            if (!promisingNode.children().isEmpty()) {
                nodeToExplore = promisingNode.randomChild(); // Assuming you implement a method to pick a random child
            }
//            int playoutResult = simulateRandomPlayout(nodeToExplore);

            // Backpropagation
//            backPropagate(nodeToExplore, playoutResult);
        }
    }


    private TicTacToeNode selectPromisingNode(TicTacToeNode rootNode) {
        TicTacToeNode currentNode = rootNode;
        while (!currentNode.children().isEmpty()) {
            double maxUCB1 = Double.MIN_VALUE;
            TicTacToeNode nodeWithMaxUCB1 = null;
            for (Node<TicTacToe> child : currentNode.children()) {
                TicTacToeNode ticTacToeChild = (TicTacToeNode) child;
                double ucb1Value = calculateUCB1(ticTacToeChild);
                if (ucb1Value > maxUCB1) {
                    maxUCB1 = ucb1Value;
                    nodeWithMaxUCB1 = ticTacToeChild;
                }
            }
            // This is the most promising node according to UCB1.
            currentNode = nodeWithMaxUCB1;
        }
        return currentNode;
    }

    private double calculateUCB1(TicTacToeNode node) {
        double c = Math.sqrt(2);
        int ni = node.playouts(); // Number of simulations for the node
        int wi = node.wins(); // Number of wins for the node
        int Ni = node.parent() != null ? node.parent().playouts() : 1; // Total simulations for the parent node, avoid division by zero

        if (ni == 0) {
            return Double.MAX_VALUE; // Always select unvisited nodes first
        }

        return ((double) wi / (double) ni) + c * Math.sqrt((2 * Math.log(Ni)) / ni);
    }


//    private int simulateRandomPlayout(TicTacToeNode node) {
//        TicTacToe.TicTacToeState tempState = (TicTacToe.TicTacToeState) node.state().clone();
//        int boardStatus = tempState.winner().orElse(-1);
//
//        while (boardStatus == -1) {
//            // Play a random move
//            // Update boardStatus
//        }
//
//        return boardStatus; // Return the result of the simulation
//    }

    private void backPropagate(TicTacToeNode nodeToExplore, int playerNo) {
        TicTacToeNode tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.setPlayouts(tempNode.getPlayouts()+1);
//            tempNode.playouts++;
            if (tempNode.state().player() == playerNo) {
                tempNode.setWins(tempNode.getWins()+1);
//                tempNode.wins++;
            }
            tempNode = (TicTacToeNode) tempNode.parent(); // Assuming you add a parent reference
        }
    }



    public MCTS(Node<TicTacToe> root) {
        this.root = root;
    }

    private final Node<TicTacToe> root;
}