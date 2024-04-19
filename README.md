# INFO6205 - Final Project  

# Program Structures and Algorithms
## Spring 2024
## FINAL PROJECT REPORT

**AUTHORS:** Kartikey Vijayakumar Hebbar (002276938), Meet Karnik (002795334)
**GITHUB LINK:** https://github.com/kartikeyhebbar/INFO6205-Final-Project

### MONTE CARLO TREE SEARCH

**Concept & Approach:**
The Monte Carlo Tree Search (MCTS) algorithm is a decision-making algorithm used primarily in artificial intelligence (AI) applications, particularly in games and situations that require optimal decision-making under uncertain conditions.

Here’s a breakdown of how it works:

**Selection:** The algorithm starts at the root node and selects successive child nodes down to a leaf node (following Depth-First Search approach). The selection is based on a policy that balances exploration of less visited nodes with exploitation of nodes known to have a high win ratio.

**Expansion:** Unless the leaf node ends the game (e.g., checkmate or win/loss in a board game), it adds one or more child nodes to expand the search tree, based on available moves.

**Simulation:** From the new nodes, the algorithm simulates random games (also known as playouts or rollouts). The outcomes of these simulations are used to estimate the node values.

**Backpropagation:** The results of the simulations are then propagated back through the tree, updating the relevant nodes with the simulation results to improve the accuracy of their value estimates.

### IMPLEMENTATION

**1. TIC-TAC-TOE**

Tic-tac-toe is a paper-and-pencil game for two players who take turns marking the spaces in a three-by-three grid with X or O. The player who succeeds in placing three of their marks in a horizontal, vertical, or diagonal row is the winner. It is a solved game, with a forced draw assuming best play from both players.

Our implementation of the Tic-Tac-Toe is a computer simulated two-player game which leverages the Monte-Carlo Tree Search (MCTS) algorithm.

There are few steps involved in the process of making any move by the CPU player:
Selection: The algorithm starts with a root node and selects a child node such that it picks the node with maximum win rate. The idea is to keep selecting optimal child nodes until we reach the leaf node of the tree. A good way to select such a child node is to use UCT (Upper Confidence Bound applied to trees) formula:

wi/ni + c(ln(t)/ni))^1/2

In which,
wi = number of wins after the i-th move
ni = number of simulations after the i-th move
c = exploration parameter (theoretically equal to √2)
t = total number of simulations for the parent node

**Expansion:** When it can no longer apply UCT to find the successor node, it expands the game tree by appending all possible states from the leaf node.

**Simulation:** The algorithm picks a child node arbitrarily, and it simulates a randomized game from the selected node until it reaches the resulting state of the game. If nodes are picked randomly or semi-randomly during the play out, it is called light play out.

**Backpropagation:** This is also known as an update phase. Once the algorithm reaches the end of the game, it evaluates the state to figure out which player has won. It traverses upwards to the root and increments visit score for all visited nodes. It also updates the win score for each node if the player for that position has won the playout.

MCTS keeps repeating these four phases until some fixed number of iterations.

**Results:** The above graph shows the average time taken for MCTS to win a TicTacToe game vs the number of iterations, also based on the number of iterations, we’ve compiled the following table which shows the amount of times X and O have won the game. Please note that all times shown are in milliseconds(ms)

**2. CONNECT 4**

Connect Four is a game in which the players choose a color/symbol and then take turns dropping their tokens into a six-row, seven-column vertically suspended grid. The pieces fall straight down, occupying the lowest available space within the column. The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own tokens.

We implemented the Connect 4 game as a 2 player (Human vs CPU) gameplay. The CPU starts the game by drawing an X on the board followed by the Human drawing an O on the game board.

For every CPU operation, MCTS algorithm is used where it follows all the four  steps as mentioned above. 

**Results:** The above graph shows the time taken for MCTS to consider all moves to  win a Connect-4 game vs the number of iterations, Please note that all times shown are in milliseconds(ms)

### REFERENCES

[https://www.researchgate.net/figure/Outline-of-Monte-Carlo-Tree-Search-adapted-from-Chaslot-et-al-39_fig2_224160689](https://www.researchgate.net/figure/Outline-of-Monte-Carlo-Tree-Search-adapted-from-Chaslot-et-al-39_fig2_224160689)  
[https://en.wikipedia.org/wiki/Monte_Carlo_tree_search](https://en.wikipedia.org/wiki/Monte_Carlo_tree_search)  
[https://www.flaticon.com/free-icon/tic-tac-toe_10199746](https://www.flaticon.com/free-icon/tic-tac-toe_10199746)  
[https://en.wikipedia.org/wiki/Tic-tac-toe](https://en.wikipedia.org/wiki/Tic-tac-toe)  
[https://www.baeldung.com/java-monte-carlo-tree-search](https://www.baeldung.com/java-monte-carlo-tree-search)  
[https://www.rd.com/article/how-to-win-connect-4/](https://www.rd.com/article/how-to-win-connect-4/)  
[https://en.wikipedia.org/wiki/Connect_Four](https://en.wikipedia.org/wiki/Connect_Four)  
[https://www.harrycodes.com/blog/monte-carlo-tree-search](https://www.harrycodes.com/blog/monte-carlo-tree-search)  