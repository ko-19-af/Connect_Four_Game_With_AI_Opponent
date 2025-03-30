import java.util.Arrays;
import java.util.Scanner; // import scanner for player input

/** This class creates a game of connect4 with an AI opponent
 *
 * The heuristic used in this AI counts the number of pieces that are connected in one of 7 directions
 * and determines if it is possible to win with that number of pieces in that direction
 *
 * IDE used:IntelliJ
 *
 * @Author Kevin Olenic
 * ST# 6814974
 * @Version 3.3
 * @Since 2021-10-10
 */

public class connectFour extends gameLogic{

    public static int[] columns = new int[6];// records the number of available positions in a column

    public connectFour(){
        AI adversary = new AI();

        Arrays.fill(columns, 6);// fill the columns with the initial value of how much they can hold
        char[][] gameBoard = new char[6][6];// create initial game board
        for(char[] c : gameBoard) Arrays.fill(c, ' ');

        Scanner scan = new Scanner(System.in); // create scanner for player input

        while(true){// create forever-loop while game is running

            if(draw()){// if game is a draw
                System.out.println("Game has ended in draw");
                break;
            }

            // compute cpu's move then place on board then print board
            placeCpuPiece(adversary.bestPlace(gameBoard), 'X', gameBoard);
            printGameBoard(gameBoard);
            if (checkWinner('X', gameBoard)){// check if cpu won game
                System.out.println("cpu has won match");
                break;
            }

            System.out.println("enter column 1-6: ");// print columns' player can choose
            int playerPos = scan.nextInt();

            placePlayerPiece(playerPos, 'O', gameBoard);//place player piece on board
            printGameBoard(gameBoard);// print game board
            if (checkWinner('O', gameBoard)) {// check if player won
                System.out.println("Player has Won match");
                break;
            }
        }
    }

    public static void main(String[] args){
        new connectFour();
    }

}