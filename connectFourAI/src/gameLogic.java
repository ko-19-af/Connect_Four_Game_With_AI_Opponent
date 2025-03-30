import java.util.Scanner;

/** This class contains all the game logic (methods) for my game of connect4
 *
 * IDE used:IntelliJ
 *
 * @Author Kevin Olenic
 * ST# 6814974
 * @Version 1.5
 * @Since 2021-10-10
 */

public class gameLogic {

    /**
     * This method prints the gameBoard
     *
     * @param board the game board being printed
     */

    public void printGameBoard(char[][] board) {

        System.out.println(" 1 2 3 4 5 6");
        for (char[] row : board) {
            for (char c : row) {
                System.out.print('|');
                System.out.print(c);
            }
            System.out.println('|');
        }
    }

    /**
     * This method checks if a player can place a piece into a column if it is not filled
     *
     * @param pos column chosen to place piece
     * @return true if column is not filled
     */
    public boolean checkColumn(int pos) {
        return connectFour.columns[pos - 1] != 0;
    }//checkColumn

    /**
     * This method checks if the columns' of the board are full and no one has won
     * resulting in a draw
     *
     * @return true if board is full
     */
    public boolean draw() {
        for (int x : connectFour.columns) if (x != 0) return false;// if there is a column not filled
        return true;// if all columns are filled
    }

    /**
     * This places the players piece in the desired column
     *
     * @param pos    is the column chosen
     * @param symbol is the player placing the piece
     * @param board  is the game board the piece is being placed on
     */
    public void placePlayerPiece(int pos, char symbol, char[][] board) {
        while (pos > 6 || pos < 1 || !checkColumn(pos)) {// while player input is invalid
            Scanner scan = new Scanner(System.in);
            System.out.println("invalid column enter valid column: ");
            pos = scan.nextInt();
        }
        connectFour.columns[pos - 1]--;// decrement the number of positions available in a column
        board[connectFour.columns[pos - 1]][pos - 1] = symbol;
    }//placePlayerPiece

    /**
     * This places the cpu's piece in the desired column
     *
     * @param pos    the column the piece is being placed in
     * @param symbol cpu piece type
     * @param board  the game board the pieces being placed on
     */

    public void placeCpuPiece(int pos, char symbol, char[][] board) {
        connectFour.columns[pos-1]--;// decrease number of spaces in appropriate column
        board[connectFour.columns[pos-1]][pos-1] = symbol;// place piece on board
        System.out.println("CPU played in column: " + pos);
    }//placeCpuPiece

    /**
     * This method checks if a player has won the game by seeing if 4 pieces are connected
     * in one of four directions
     *
     * @param piece the piece being checked if it won
     * @param board the board game being checked for a winner
     * @return true if player won
     */

    public boolean checkWinner(char piece, char[][] board) {
        int[][] direction = {{-1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, -1}};
        for (int y = board.length - 1; y >= 0; y--) {// cycle through the rows and columns of the grid (start at base)
            for (int x = 0; x < board.length; x++) {
                for (int[] d : direction) if (check(x, y, piece, board, d)) return true;// if direction has four pieces
            }
        }
        return false;//if none return true return false
    }//checkWinner

    /**
     * This method checks if a player has four connected pieces in a direction
     * @param x is the column of the bord being looked at
     * @param y is the row of the board being looked at
     * @param piece is the player being checked
     * @param board is the board being examined
     * @param d is the direction being looked at
     * @return true if there is a winner
     */
    private boolean check(int x, int y, char piece, char[][] board, int[] d) {
        for (int m = 0; m < 4; m++) {// check four places on the board
            if (y + (d[0] * m) >= board.length || y + (d[0] * m) < 0
                    || x + (d[1] * m) >= board.length || x + (d[1] * m) < 0) return false;// if moved out of bounds

            if (piece != board[y + (d[0] * m)][x + (d[1] * m)]) return false;// if piece does not match
        }
        return true;// return true if all four pieces are the same
    }
}//gameLogic