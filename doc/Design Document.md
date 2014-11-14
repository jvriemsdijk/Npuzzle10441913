Design Document
===============

By: Joey van Riemsdijk (10441913)
---------------------------------

This document is intended to detail the technical implentation of the N Puzzle app.

Activities
----------

The app will use three different activities :
 - ImageSelection
 - GamePlay
 - YouWin

### ImageSelection ###

This activity will be used as the starting point of the app. This is where users will select their difficulty and image to play with. On this activity there will be a button which allows a user to immediatly start a game with a medium difficulty and a rondom image

### GamePlay ###

This activity is used to actually play the game. It is here that the image will be displayed and the user can tap on the game pieces to make their move. The layout of this activity will be a gridlayout in order to easily implement an onItemClick method for the game pieces.

### YouWin ###

YouWin in the last activity in the app and will be shown once the user has succesfully completed the game, after which the user can return back to the ImageSelection.



Classes
-------

The following section will detail the classes used in the application.

### Tile ###

This class will represent one tile of the game board. It will contain the following:
 - The image part it represents (BitMap)
 - If it is the blank tile (boolean)
 - Its original position, aka the position it should have for a game win (int)

### GameBoard ###

This class is used for maintaining the board state. It contains:
 - A Map with all the board positions as keys and the Tiles as values (Map<int, Tile>)
 - A function which checks if the game is won
 - Number of moves made by the user
 - Difficulty
 - Time spent?
 - Original scrambeled state



Methods
-------

This section will give some more information on the classes that are to be implemented into the Npuzzle app.

### selectDifficulty(int difficulty) ###
As the name implies this method will set the difficulty that the game will use for the rest of the session.

### splitImage(BitMap image) ###
This method will take the given image and split it according to the selected difficulty with the help of a BitmapFactory, and will return a GameBoard in a solved state.

### scrambleBoard(GameBoard gameBoard) ###
This mehtod will be used to scramble the board to a solvable state. This is done by making the app make an X amount of random (and non repeating) moves from a solved board state. X is yet to be determined, since this will depend on the performance and if the board is scrambled enough.

### startGame() ###
Changes the activity to GamePlay and displays the gameboard

### swapTiles(GameBoard gameBoard, Tile tile) ###
This method will first check if the given move is legal. If not this method does nothing, if it is a legal move the tile positions will be swapped. Lastly a check is done if the game is won.

### gameWon() ###
This method is called as soon as the game is won. It will change the activity to GameWon

### onItemClick(View view) ###
This method will call the swapTiles method with the current game board and the tapped tile.

### onPause() ###
Used to save the data as soon as the app is removed from the foreground, this saves the GameBoard in a bundle

### onResume() ###
Recreates the GameBoard from the saved bundle

Mockups
-------

![mockup](https://github.com/jvriemsdijk/Npuzzle10441913/blob/master/doc/Mockups.png)

