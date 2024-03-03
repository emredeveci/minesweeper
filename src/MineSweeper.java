import java.util.Objects;
import java.util.Scanner;
import java.util.Random;

public class MineSweeper {

    int rows;
    int columns;
    int mineCount;
    boolean isLost = false;
    String[][] userSideBoard;
    String[][] clientSideBoard;

    Scanner scanner = new Scanner(System.in);

    //ask the user for row and column numbers in the beginning of the game
    void userBoardInputs() {
        System.out.println("\uD83D\uDCA3 CREATE YOUR FIELD \uD83D\uDCA3");
        System.out.println("Enter the number of rows: ");
        rows = scanner.nextInt();
        System.out.println("Enter the number of columns: ");
        columns = scanner.nextInt();

        //assign values to variables that depend on user inputs
        userSideBoard = new String[rows][columns];
        clientSideBoard = new String[rows][columns];
        mineCount = (rows * columns) / 4;
    }

    void createUserBoard() {
        for (String[] row : userSideBoard) {
            for (String cell : row) {
                cell = "-";
                System.out.print(cell + " ");
            }
            System.out.println("");
        }
    }

    void createClientSideBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                clientSideBoard[i][j] = "-";
            }
        }
    }

    void fillWithMines() {
        Random randomizer = new Random();
        int remainingMines = mineCount;

        while (remainingMines > 0) {
            int randomRow = randomizer.nextInt(this.rows);
            int randomColumn = randomizer.nextInt(this.columns);

            if (Objects.equals(clientSideBoard[randomRow][randomColumn], "*")) {
                continue;
            } else {
                clientSideBoard[randomRow][randomColumn] = "*";
                remainingMines--;
            }
        }
    }

    void printClientSideBoard() {
        for (String[] row : clientSideBoard) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println("");
        }
    }

    void updateUserBoard(int rowGuess, int columnGuess) {
        userSideBoard[rowGuess][columnGuess] = "0";

        for (String[] row : userSideBoard) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println("");
        }

    }

    void userGameInputs() {
        int rowGuess, columnGuess;
        while (!isLost) {
            System.out.println("Enter a row number: ");
            rowGuess = scanner.nextInt();
            System.out.println("Enter a column number: ");
            columnGuess = scanner.nextInt();

            if(Objects.equals(clientSideBoard[rowGuess][columnGuess], "*")){
                System.out.println("You lost the game.");
                isLost = true;
            } else {
                updateUserBoard(rowGuess, columnGuess);
                System.out.println("wow, damn!");
            }

        }

    }

    void run() {
        userBoardInputs();
        createUserBoard();
        createClientSideBoard();
        fillWithMines();
//        printClientSideBoard();
        userGameInputs();
    }
}
