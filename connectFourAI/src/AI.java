public class AI extends gameLogic {

    public AI() {
    }// constructor

    /**
     * This method is the AI base of the game where it uses the minimax algorithm to decide the best move
     * then assigns the values into the move Node
     *
     * @param board is the environment the AI is working with (game board)
     * @return is the column that is the best move for the cpu
     */

    public int bestPlace(char[][] board) {
        int bestValue = Integer.MIN_VALUE, column = 0;

        for (int y = board.length - 1; y >= 0; y--) {// start from base of board
            for (int x = 0; x < board.length; x++) {

                // if valid position (i.e. empty base position or position with piece below it)
                if ((y == board.length - 1 && board[y][x] == ' ') ||
                        (y != board.length - 1 && board[y][x] == ' ' && board[y + 1][x] != ' ')){
                    board[y][x] = 'X';// place cpu piece
                    // evaluate using minimax
                    int moveValue = Math.max(bestValue, minimax(3, board, false));
                    board[y][x] = ' ';// undo the move

                    if (moveValue > bestValue) {// if move is better than current best
                        bestValue = moveValue;// reassign bestValue and column variables
                        column = x+1;// reassign best move column
                    }
                }
            }
        }
        return column;// return column cpu is playing in
    }//bestPlace

    /**
     * This method is the minimax algorithm it will generate the children nodes and
     * determine return the best/worst score for maximizing/minimizing the cpu/opponent
     *
     * @param depth how many moves forward the AI is looking
     * @param board the child node of the parent node
     * @param maximize whether we are maximizing the cpu or minimizing the player
     * @return the score of a board (move)
     */

    private int minimax(int depth, char[][] board, boolean maximize) {
        int value = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;// set initial value for move
        // if depth = 0 or one of the players has won
        if (checkWinner('X', board)) return 100000;// if cpu won return highest point
        else if (checkWinner('O', board)) return -100000;// if cpu lost return lowest point
        else if (depth == 0) return heuristic(board);// if neither win return heuristic score of boa

        if (maximize){// maximize CPU
            for (int y = board.length - 1; y >= 0; y--) {// cycle through the rows and columns to create child nodes
                for (int x = 0; x < board.length; x++) {
                    if ((y == board.length-1 && board[y][x] == ' ') || (y != board.length-1
                            && board[y][x] == ' ' && board[y+1][x] != ' ')){// if a valid spot
                        board[y][x] = 'X'; // set position to cpu piece
                        value = Math.max(value, minimax(depth - 1, board, false));// set best as max value
                        board[y][x] = ' '; // reset to blank space
                    }
                }
            }
        } else {// minimizing player
            for (int y = board.length-1; y >= 0; y--) {// cycle through rows and columns making child nodes
                for (int x = 0; x < board.length; x++) {
                    if ((y == board.length-1 && board[y][x] == ' ') || (y != board.length-1
                            && board[y][x] == ' ' && board[y + 1][x] != ' ')){
                        board[y][x] = 'O';// set position to opponent piece
                        value = Math.min(value, minimax(depth - 1, board, true));// set worse as min value
                        board[y][x] = ' ';// reset to blank piece
                    }
                }
            }
        }
        return value;// return the best value
    }//minimax

    /**
     * This method is the heuristic it evaluates the score of boards created by the minimax
     * by counting the number of pieces connected in 7 directions
     * and whether you can win with those pieces in one move
     *
     * @param board the board being evaluated
     * @return the score of the board
     */

    private int heuristic(char[][] board) {
        int playerScore = 0, cpuScore = 0; // initialize player and cpu score
        int[][] direction = {{-1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, 1}, {1, -1}}; // directions to search

        for (int y = board.length-1; y >= 0; y--) {// cycle through the rows and columns of the grid
            for (int x = 0; x < board.length; x++){
                if(board[y][x] == ' ') continue;// if blank space skip
                int best = Integer.MIN_VALUE;// initialize the best for the position
                // determine which direction has best score
                for(int[] d : direction) best = Math.max(count(x,y,board,board[y][x],d), best);
                if (board[y][x] == 'O' && playerScore < best) playerScore = best;// if it's the player
                else if (cpuScore < best) cpuScore = best;// else it is the cpu
            }
        }
        if(playerScore > cpuScore) return -1 * (playerScore);// if player is winning return negative player score value
        else return cpuScore;//if cpu is winning or tied with player return cpu score
    }//heuristic

    /**
     * This method counts the number of player tiles in a specific direction
     * @param x the column being examined
     * @param y the row being examined
     * @param board the game board being examined
     * @param symbol the piece type being looked at
     * @param d the direction being look at
     * @return the score
     */

    private int count(int x, int y, char[][] board, char symbol, int[] d) {
        int score = 0;

        for (int m = 0; m < 4; m++) {// for each movement
            if (y + (d[0]*m) >= board.length || y + (d[0]*m) < 0
                    || x + (d[1]*m) >= board.length || x + (d[1]*m) < 0) return 0;// if moved out of bounds
            else if (board[y + (d[0]*m)][x + (d[1]*m)] == symbol) score++;// if board piece matches
            else break;
        }

        if (board[y + (d[0] * score)][x + (d[1] * score)] != ' ') return score - 1;// if blocked at next position

        else if (score == 3 && board[y + (d[0] * score)][x + (d[1] * score)] == ' ') return 100;// if one from winning

        else if (score == 2 && board[y + (d[0] * score)][x + (d[1] * score)] == ' '
                && board[y + (d[0] * score)][x + (d[1] * score)] == ' ') return 50;// if two from winning

        else if (score == 1 && (board[y + (d[0] * score)][x + (d[1] * score)] == ' '
                && board[y + (d[0] * score)][x + (d[1] * score)] == ' '
                && board[y + (d[0] * score)][x + (d[1] * score)] == ' ')) {
            return 25;// if three from winning
        }
        return score;
    }//count
}//gameLogic
