# Minesweeper Game



A non-GUI take on the classic Minesweeper game in which a player needs to successfully uncover all the cells that do not contain mines to win the game.

## Technologies

- This project has been developed using JAVA SE 8.

## Installation

1. Clone the repository to your local machine that has a Java Development Kit.
2. Compile and run the ```Main.java``` file using the IDE of your choice.

## Code Structure
The repository contains two code files:

1. **Main.java**: This file creates an instance of the ```MineSweeper``` class and starts the game.
2. **Minesweeper.java**: This file has all the necessary game logic.

## Game Process

- **User Inputs**: When the game runs, the player is asked for inputs on which the size of the game board and mine count will be based on.
- **Board Creation**: Based on the user inputs, a game board is created. Then, the game board is randomly filled with mines.
- **Gameplay**: Following the board creation, the player starts entering locations to reveal. If the player reveals a location without mines, the game shows how many mines there are in the surrounding cells and asks for their next guess. This continues until either the player successfully uncovers all the locations without mines and wins, or reveals a cell with a mine and loses.
- **Win or Loss**: Depending on the outcome the game displays a win or loss message to the user, and is terminated.

## Game Logic

- **Initialization**: ```run``` method initializes the game.
- **Standard Inputs**: ```userBoardInputs``` method asks the user for the board size they want to create and play. These inputs help initialize the two dimensional arrays that the game board is created with.
- **Mine Placement**: ```placeMines``` method randomly places mines on the game board using a nested loop.
- **Gameplay** : ```playGame``` method starts the process of receiving player inputs for the cells they want to reveal.
    - For each guess, the code first tests for valid entries with the ```exists``` method. 
    - If the cell has already been entered, the player is notified and asked to enter another location.
    - If the cell has a mine, ```hasMine``` method returns true, and the ```isGameOver``` method ends the game with the relevant message shown to the player.
    - If the cell does not have a mine, the ```surroundingMines``` method calculates the number of mines around the revealed cell and assigns their total number to it. The ```minelesssTiles``` variable is incremented, and the player is then shown an updated board and asked to enter a new guess. 
    - If the player successfully reveals all the cells without mines (when ```minelessTiles``` variable reaches zero), the game ends with the relevant message shown to the player.

    
## Authors

- [@emredeveci](https://github.com/emredeveci)


## License

[MIT](https://choosealicense.com/licenses/mit/)

