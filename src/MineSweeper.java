import java.util.Objects;
import java.util.Scanner;
import java.util.Random;

//EVALUATION FORM 5
//All the game related code is in MineSweeper class
public class MineSweeper {

    //EVALUATION FORM 1
    //variables and methods are named after what they do/are
    int rows;
    int columns;
    int mineCount;
    int unopenedTiles;
    boolean isGameOver = false;
    boolean invalidBoardSize = false;
    String[][] userSideBoard;
    String[][] clientSideBoard;

    //initialize the scanner
    Scanner scanner = new Scanner(System.in);

    //EVALUATION FORM 7
    //ask the user for row and column numbers in the beginning of the game
    //if the board size is invalid, keep asking the user for new inputs
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
        this.userSideBoard = new String[rows][columns];
        this.clientSideBoard = new String[rows][columns];
        this.mineCount = (rows * columns) / 4;
        this.unopenedTiles = rows * columns;
    }

    //This method creates and prints the board that the USER sees
    void createUserSideBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                userSideBoard[i][j] = "-";
                System.out.print(userSideBoard[i][j] + " ");
            }
            System.out.println("");
        }
    }

    //This method creates the client side board (to be used as a blueprint of the minefield)
    //This board isn't visible to the player
    void createClientSideBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                clientSideBoard[i][j] = "-";
            }
        }
    }

    //EVALUATION FORM 8
    //This method randomly place mines on the client side board
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

    //This method can print the client side board. By default, it is NOT called when the game runs.
    void printClientSideBoard() {
        for (String[] row : clientSideBoard) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println("");
        }
        System.out.println("============");
    }

    //EVALUATION FORM 15
    //This method is responsible for changing the game state (over or not)
    //It also prints the correct message depending on the win or loss
    void isGameOver(int unopenedTiles) {
        if (unopenedTiles == mineCount) {
            System.out.println("Congratulations! You won.");
            this.isGameOver = true;
        } else {
            System.out.println("Bad luck. You lost.");
            this.isGameOver = true;
        }
    }

    //This method checks if a location/coordinate exists in the current board
    boolean exists(int row, int col) {
        return (row < this.rows && row >= 0 && col < this.columns && col >= 0);
    }

    //This method checks if a given location has a mine
    boolean hasMine(int row, int col) {
        if (exists(row, col)) {
            return (Objects.equals(clientSideBoard[row][col], "*"));
        }
        return false;
    }

    //This method checks if the existing & surrounding tiles has mines
    String surroundingMines(int rowGuess, int columnGuess) {
        int adjacentMines = 0;

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

    //EVALUATION FORM 12
    //This method updates the user side board and prints it after each reveal
    void updateUserBoard(int rowGuess, int columnGuess) {
        userSideBoard[rowGuess][columnGuess] = surroundingMines(rowGuess, columnGuess);

        for (String[] row : userSideBoard) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println("");
        }
    }

    //EVALUATION FORM 9, 10, 11, 13, 14
    //This method keeps asking the user for coordinates
    //It loops back when the user enters invalid locations
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
                isGameOver(unopenedTiles);
            } else {
                updateUserBoard(rowGuess, columnGuess);
                unopenedTiles--;
                isGameOver(unopenedTiles);
            }
        }
    }

    //This method calls the above methods in the correct order for the game to function
    void run() {
        userBoardInputs();
        createClientSideBoard();
        fillWithMines();
        printClientSideBoard();
        createUserSideBoard();
        userGameInputs();
    }
}
