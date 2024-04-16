package edu.neu.coe.info6205.mcts.connectfour;

public class Main {
    public static void main(String[] args) {
        Game connectFour = new Game(new ConnectFourBoard(7),
                new Computer(1, 4000),
                new HumanPlayer(2)
        );
        connectFour.play();
    }
}
