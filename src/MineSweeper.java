import java.util.Objects;
import java.util.Scanner;
import java.util.Random;

//EVALUATION FORM 5
//All the game related code is in MineSweeper class
public class MineSweeper {

    //EVALUATION FORM 1
    //variables and methods are named after what they do/are
    int rows, columns, mineCount, minelessTiles;
    boolean isGameOver = false, invalidBoardSize = false;
    String[][] userBoard, serverBoard;

    //initialize the scanner
    Scanner scanner = new Scanner(System.in);

    //EVALUATION FORM 6, 7
    //ask the user for row and column numbers in the beginning of the game
    void userBoardInputs() {
        System.out.println("\uD83D\uDCA3 WELCOME TO MINESWEEPER \uD83D\uDCA3");

        do {
            System.out.print("Enter the number of rows: ");
            rows = scanner.nextInt();
            System.out.print("Enter the number of columns: ");
            columns = scanner.nextInt();

            if (rows < 2 || columns < 2) {
                System.out.println("The rows and/or columns cannot be lower than 2. Try again: ");
            } else {
                invalidBoardSize = true;
            }
        } while (!invalidBoardSize);

        //assign values to variables that depend on user inputs
        this.userBoard = new String[rows][columns];
        this.serverBoard = new String[rows][columns];
        this.mineCount = (rows * columns) / 4;
        this.minelessTiles = (rows * columns) - mineCount;
    }

    //Create a board
    void createBoard(String[][] boardType) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boardType[i][j] = "-";
            }
        }
    }

    //Print a board
    void printBoard(String[][] boardType) {
        for (String[] row : boardType) {
            for (String cell : row) {
                System.out.print(cell + "   ");
            }
            System.out.println("");
        }
        System.out.println("============");
    }

    //EVALUATION FORM 8
    //This method randomly places mines on the server side board
    void placeMines() {
        Random randomizer = new Random();
        int remainingMines = mineCount;

        while (remainingMines > 0) {
            int randomRow = randomizer.nextInt(this.rows);
            int randomColumn = randomizer.nextInt(this.columns);

            if (Objects.equals(serverBoard[randomRow][randomColumn], "*")) {
                continue;
            } else {
                serverBoard[randomRow][randomColumn] = "*";
                remainingMines--;
            }
        }
    }

    //EVALUATION FORM 6, 13, 14, 15
    //This method is responsible for changing the game state (over or not) and printing the outcome
    void isGameOver(boolean clickedMine, int minelessTiles) {
        if (clickedMine || minelessTiles == 0) {
            if (clickedMine) { //EVALUATION FORM 13
                System.out.println("Bad luck! You lost.");
            } else if (minelessTiles == 0) { //EVALUATION FORM 14
                System.out.println("Congratulations! You won.");
            }
            isGameOver = true;
        }
    }

    //This method checks if a location/coordinate exists in the current board
    boolean exists(int row, int col) {
        return (row < this.rows && row >= 0 && col < this.columns && col >= 0);
    }

    //This method checks if a given location has a mine
    boolean hasMine(int row, int col) {
        if (exists(row, col)) {
            return (Objects.equals(serverBoard[row][col], "*"));
        }
        return false;
    }

    //This method checks if the existing & surrounding tiles have mines
    String surroundingMines(int row, int col) {
        int surroundingMines = 0;

        if (hasMine(row - 1, col - 1) && exists(row - 1, col - 1)) {
            surroundingMines++;
        }
        if (hasMine(row - 1, col) && exists(row - 1, col)) {
            surroundingMines++;
        }
        if (hasMine(row - 1, col + 1) && exists(row - 1, col + 1)) {
            surroundingMines++;
        }
        if (hasMine(row, col - 1) && exists(row, col - 1)) {
            surroundingMines++;
        }
        if (hasMine(row, col + 1) && exists(row, col + 1)) {
            surroundingMines++;
        }
        if (hasMine(row + 1, col - 1) && exists(row + 1, col - 1)) {
            surroundingMines++;
        }
        if (hasMine(row + 1, col) && exists(row + 1, col)) {
            surroundingMines++;
        }
        if (hasMine(row + 1, col + 1) && exists(row + 1, col + 1)) {
            surroundingMines++;
        }

        return Integer.toString(surroundingMines);
    }

    //EVALUATION FORM 11, 12
    //This method updates the user side board after each guess
    void updateUserBoard(int row, int col) {
        userBoard[row][col] = surroundingMines(row, col);
        printBoard(userBoard);
    }

    //EVALUATION FORM 6, 9, 10, 11, 13, 14
    //This method keeps asking the user for coordinates, and loops until the user's entry is valid
    void playGame() {
        int row, col;

        while (!isGameOver) {
            System.out.print("Enter a row number: ");
            row = scanner.nextInt();
            System.out.print("Enter a column number: ");
            col = scanner.nextInt();

            if (!exists(row, col)) {//EVALUATION FORM 10
                System.out.println("You have entered an invalid location. Please try again.");
                continue;
            } else if (!Objects.equals(userBoard[row][col], "-")) {
                System.out.println("You have already revealed this location. Please enter a different row and column.");
                continue;
            } else if (hasMine(row, col)) {//EVALUATION FORM 13
                isGameOver(true, minelessTiles);
            } else {
                updateUserBoard(row, col);//EVALUATION FORM 11
                minelessTiles--;
                isGameOver(false, minelessTiles);//EVALUATION FORM 14
            }
        }

        scanner.close();
    }

    //This method calls the above methods in the correct order for the game to function
    void run() {
        userBoardInputs();
        createBoard(userBoard);
        createBoard(serverBoard);
        placeMines();
//        System.out.println("M  A  P");
//        printBoard(serverBoard);
        System.out.println("G A M E   B O A R D");
        printBoard(userBoard);
        playGame();
    }
}
