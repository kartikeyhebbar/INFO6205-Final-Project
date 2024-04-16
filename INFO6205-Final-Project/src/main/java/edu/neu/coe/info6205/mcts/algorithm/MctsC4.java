package edu.neu.coe.info6205.mcts.algorithm;

import edu.neu.coe.info6205.mcts.utils.Board;

import java.time.Instant;

public class MctsC4 {

    private final Board board;

    private final int playerId;

    private final int opponentId;

    private final int computations;

    public MctsC4(Board board, int playerId, int computations) {
        this.board = board;
        this.playerId = playerId;
        this.opponentId = playerId % 2 + 1;
        this.computations = computations;
    }

    public Board doMcts() {
        System.out.println("MCTS working.");
        Instant start = Instant.now();

        long counter = 0l;

        NodeC4 tree = new NodeC4(board);

        while (counter < computations) {
            counter++;

            //SELECT
            NodeC4 promisingNode = selectPromisingNode(tree);

            //EXPAND
            NodeC4 selected = promisingNode;
            if (selected.board.getStatus() == Board.GAME_IN_PROGRESS) {
                selected = expandNodeAndReturnRandom(promisingNode);
            }

            //SIMULATE
            int playoutResult = simulateLightPlayout(selected);

            //PROPAGATE
            backPropagation(playoutResult, selected);
        }

        NodeC4 best = tree.getChildWithMaxScore();

        Instant end = Instant.now();
        long milis = end.toEpochMilli() - start.toEpochMilli();

        System.out.println("Did " + counter + " expansions/simulations within " + milis + " milis");
        System.out.println("Best move scored " + best.score + " and was visited " + best.visits + " times");

        return best.board;
    }

    // if node is already a leaf, return the leaf
    private NodeC4 expandNodeAndReturnRandom(NodeC4 node) {
        NodeC4 result = node;

        Board board = node.board;

        for (Board move : board.getAllLegalNextMoves()) {
            NodeC4 child = new NodeC4(move);
            child.parent = node;
            node.addChild(child);

            result = child;
        }

        int random = Board.RANDOM_GENERATOR.nextInt(node.children.size());

        return node.children.get(random);
    }

    private void backPropagation(int playerNumber, NodeC4 selected) {
        NodeC4 node = selected;

        while (node != null) { // look for the root
            node.visits++;
            if (node.board.getLatestMovePlayer() == playerNumber) {
                node.score++;
            }

            node = node.parent;
        }
    }

    /**
     *
     * "Light playout" is to indicate that each move is chosen totally randomly,
     * in contrast to using some heuristic
     *
     */
    private int simulateLightPlayout(NodeC4 promisingNode) {
        NodeC4 node = new NodeC4(promisingNode.board);
        node.parent = promisingNode.parent;
        int boardStatus = node.board.getStatus();

        if (boardStatus == opponentId) {
            node.parent.score = Integer.MIN_VALUE;
            return node.board.getStatus();
        }

        while (node.board.getStatus() == Board.GAME_IN_PROGRESS) {
            //game.ConnectFourBoard nextMove = node.board.getWinningMoveOrElseRandom();
            Board nextMove = node.board.getRandomLegalNextMove();

            NodeC4 child = new NodeC4(nextMove);
            child.parent = node;
            node.addChild(child);

            node = child;
        }

        return node.board.getStatus();
    }

    private NodeC4 selectPromisingNode(NodeC4 tree) {
        NodeC4 node = tree;
        while (node.children.size() != 0) {
            //if (node.children.size() != 0) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }
}