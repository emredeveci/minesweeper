import java.util.Objects;
import java.util.Scanner;
import java.util.Random;

public class MineSweeper {

    int rows;
    int columns;
    int mineCount;
    int unopenedTiles;
    boolean isGameOver = false;
    boolean invalidBoardSize = false;
    String[][] userSideBoard;
    String[][] clientSideBoard;

    Scanner scanner = new Scanner(System.in);

    //ask the user for row and column numbers in the beginning of the game
    void userBoardInputs() {
        System.out.println("\uD83D\uDCA3 CREATE YOUR FIELD \uD83D\uDCA3");

        do {
            System.out.println("Enter the number of rows: ");
            rows = scanner.nextInt();
            System.out.println("Enter the number of columns: ");
            columns = scanner.nextInt();

            if (rows < 2 || columns < 2) {
                System.out.println("The rows and/or columns cannot be lower than 2. Try again: ");
            } else {
                invalidBoardSize = true;
            }
        } while (!invalidBoardSize);

        //assign values to variables that depend on user inputs
        userSideBoard = new String[rows][columns];
        clientSideBoard = new String[rows][columns];
        mineCount = (rows * columns) / 4;
        unopenedTiles = rows * columns;
    }

    void createUserBoard() {
//        for (String[] row : userSideBoard) {
//            for (String cell : row) {
//                cell = "-";
//                System.out.print(cell + " ");
//            }
//            System.out.println("");
//        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                userSideBoard[i][j] = "-";
                System.out.print(userSideBoard[i][j] + " ");
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
        System.out.println("============");
    }

    void gameOver() {
        System.out.println("You have lost the game");
    }

    //check if a location exists in the current board
    boolean exists(int row, int col) {
        return (row < this.rows && row >= 0 && col < this.columns && col >= 0);
    }

    //check if an existing location has mines
    boolean hasMine(int row, int col) {
        if (exists(row, col)) {
            return (Objects.equals(clientSideBoard[row][col], "*"));
        }
        return false;
    }

    String adjacentMines(int rowGuess, int columnGuess) {
        int adjacentMines = 0;

        //checking the boxes above the user's pick
        if (hasMine(rowGuess - 1, columnGuess - 1) && exists(rowGuess - 1, columnGuess - 1)) {
            adjacentMines++;
        }
        if (hasMine(rowGuess - 1, columnGuess) && exists(rowGuess - 1, columnGuess)) {
            adjacentMines++;
        }
        if (hasMine(rowGuess - 1, columnGuess + 1) && exists(rowGuess - 1, columnGuess + 1)) {
            adjacentMines++;
        }
        if (hasMine(rowGuess, columnGuess - 1) && exists(rowGuess, columnGuess - 1)) {
            adjacentMines++;
        }
        if (hasMine(rowGuess, columnGuess + 1) && exists(rowGuess, columnGuess + 1)) {
            adjacentMines++;
        }
        if (hasMine(rowGuess + 1, columnGuess - 1) && exists(rowGuess + 1, columnGuess - 1)) {
            adjacentMines++;
        }
        if (hasMine(rowGuess + 1, columnGuess) && exists(rowGuess + 1, columnGuess)) {
            adjacentMines++;
        }
        if (hasMine(rowGuess + 1, columnGuess + 1) && exists(rowGuess + 1, columnGuess + 1)) {
            adjacentMines++;
        }

        return Integer.toString(adjacentMines);
    }

    void updateUserBoard(int rowGuess, int columnGuess) {
        userSideBoard[rowGuess][columnGuess] = adjacentMines(rowGuess, columnGuess);

        for (String[] row : userSideBoard) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println("");
        }
    }

    void userGameInputs() {
        int rowGuess, columnGuess;

        while (!isGameOver) {
            System.out.println("Enter a row number: ");
            rowGuess = scanner.nextInt();
            System.out.println("Enter a column number: ");
            columnGuess = scanner.nextInt();

            if (!exists(rowGuess, columnGuess)) {
                System.out.println("You have entered an invalid location. Please try again.");
                continue;
            } else if (hasMine(rowGuess, columnGuess)) {
                System.out.println("You lost the game.");
                isGameOver = true;
            } else {
                updateUserBoard(rowGuess, columnGuess);
                unopenedTiles--;

                if (unopenedTiles == mineCount) {
                    System.out.println("You won the game!");
                    isGameOver = true;
                }
            }
        }
    }

    void run() {
        userBoardInputs();
        createClientSideBoard();
        fillWithMines();
        printClientSideBoard();
        createUserBoard();
        userGameInputs();
    }
}
