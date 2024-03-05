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
    int minelessTiles;
    boolean isGameOver = false;
    boolean invalidBoardSize = false;
    String[][] userBoard;
    String[][] serverBoard;

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
        this.userBoard = new String[rows][columns];
        this.serverBoard = new String[rows][columns];
        this.mineCount = (rows * columns) / 4;
        this.minelessTiles = (rows * columns) - mineCount;
    }

    //This method creates and prints the board that the USER sees
    void createUserBoard() {

        System.out.println("G A M E   B O A R D");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                userBoard[i][j] = "-";
                System.out.print(userBoard[i][j] + "   ");
            }
            System.out.println("");
        }
    }

    //This method creates the server side board (to be used as a blueprint of the minefield)
    //This board isn't visible to the player
    void createServerBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                serverBoard[i][j] = "-";
            }
        }
    }

    //EVALUATION FORM 8
    //This method randomly places mines on the server side board
    void fillWithMines() {
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

    //This method prints the server side board. By default, it is NOT called when the game runs.
    void printServerBoard() {

        System.out.println("M A P");
        for (String[] row : serverBoard) {
            for (String cell : row) {
                System.out.print(cell + "   ");
            }
            System.out.println("");
        }
        System.out.println("============");
    }

    //EVALUATION FORM 15
    //This method is responsible for changing the game state (over or not)
    //It also prints the correct message depending on the win or loss
    void isGameOver(boolean clickedMine, int minelessTiles) {

        if (clickedMine || minelessTiles == 0) {
            if (clickedMine) {
                System.out.println("Bad luck! You lost.");
            } else if (minelessTiles == 0) {
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
    //This method updates the user side board and prints it after each reveal
    void updateUserBoard(int row, int col) {
        userBoard[row][col] = surroundingMines(row, col);

        for (String[] rows : userBoard) {
            for (String cell : rows) {
                System.out.print(cell + "   ");
            }
            System.out.println("");
        }
    }

    //EVALUATION FORM 9, 10, 11, 13, 14
    //This method keeps asking the user for coordinates and loops until the user's entry is valid
    void playGame() {
        int row, col;

        while (!isGameOver) {
            System.out.println("Enter a row number: ");
            row = scanner.nextInt();
            System.out.println("Enter a column number: ");
            col = scanner.nextInt();

            if (!exists(row, col)) {//EVALUATION FORM 10
                System.out.println("You have entered an invalid location. Please try again.");
                continue;
            } else if (!Objects.equals(userBoard[row][col], "-")){
                System.out.println("You have already revealed this location. Please enter a different row and column.");
                continue;
            } else if (hasMine(row, col)) {//EVALUATION FORM 13
                isGameOver(true, minelessTiles);
            } else {
                updateUserBoard(row, col);
                minelessTiles--;
                isGameOver(false, minelessTiles);//EVALUATION FORM 14
            }
        }
    }

    //This method calls the above methods in the correct order for the game to function
    void run() {
        userBoardInputs();
        createServerBoard();
        fillWithMines();
        printServerBoard();
        createUserBoard();
        playGame();
    }
}
