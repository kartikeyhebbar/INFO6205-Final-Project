package edu.neu.coe.info6205.mcts.connectfour;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Long> benchmark = new ArrayList<>();
    public static void main(String[] args) {
        Game connectFour = new Game(new ConnectFourBoard(7),
                new Computer(1, 4000),
                new HumanPlayer(2)
        );
        connectFour.play();

        // Show benchmarks for the game
        Long totalBenchmark = 0l;
        for(Long b: benchmark) {
            totalBenchmark += b;
        }
        System.out.println("All MCTS operations took: " + totalBenchmark + " milliseconds");
    }
}
