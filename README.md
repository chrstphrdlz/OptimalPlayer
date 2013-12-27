OptimalPlayer
=============

Will play a Tick Tac Toe game that will never lose. The AI will always choose the move which will maximize the amount of possible wins that can be made. It uses a decision tree structure that has been memoized in an array structure. This will make it so that games with the same board do not have to be re-calculated (for example moving to the top right then the bottom left is essentially the same as moving to the bottom left then the top right).

The tiles are numbered as follows:

<table>
	<tr>
		<td></td>
		<td>0</td>
		<td>1</td>
		<td>2</td>
	</tr>
	<tr>
		<td>0</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>1</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>2</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
<table>

The input is as follows:
Type X + Enter
Type Y + Enter


# Contents
- [EnemyPlayer](#enemyplayer) 
- [DecisionTreeNode](#decisiontreenode) 
- [TickTacToe](#tictactoe) 
- [Tile](#tile) 
- [PlayTickTacToe](#playticktactoe) 
- [Debug](#debug) 

## EnemyPlayer ##
This class represents the game with an enemy AI playing against the user. It can be an X player or an O player. It uses a decision tree structure to maximize the win/loss ratio. It will always choose a decision that will not result in the opponet winning.

## DecisionTreeNode ##
This is the class used to create a memoized decision tree for the enemy AI. Each node has a game state accociated with it, and a list of all decision nodes that can be made by making a move from the current game state (nextNode). If the current game has a winner, the game is done and there are no more decisions to make. This decision tree has all possible moves that can be possibly made by two players. The decision tree also has a tile representing the player who will win (most of the time it is Empty, unless it is inevitable that one of the players will win).

## TickTacToe ##
Provides a class that represents a game board. The state of a game board can be thought of as an integer, with each tile representing a base 3 number (0 = Empty, 1 = X, 2 = O). This integer is later used to memoize all possible games. There is a constructor to make a game from an integer, and a method to get the integer representation (findPlaceInAllPossibleGames). There are methods used to see if the game has any winners and for a player to make a move on a specific tile.

## Tile ##
This tile enumeration represents one of the 9 squares in the Tick Tack Toe board. It also represents the types of winning (either a tile type: X, O, or nothing, or a tie). It has a toString method to allow each individual tile to print on the game board. The winVal associated with each tile is what is attempting to be maximized (if the ai is playing as X, we want to make X positive and O negative). For example, if we want to make X win, we call that method and it makes the X winVal 1 and O -1.

## PlayTickTacToe ## 
This is the main running class. It just gets the user's command line arguments and plays a game with the user being an X or an O. It can also run a series of test cases (-test) with an input of moves. It has been tested with all possible moves with no losses for the enemy AI. In test mode, 0-8 is used to represent the tile to move to and moves that cannot be done are simply skipped. All permutations of 0-8 were used as an input to prove that there are no losses.

## Debug ##
This directory is used to generate all possible moves that the user can do to test against the enemy AI. generateAllTestCases.c is used to output all permutations of 012345678 to a text fle. After this is done, we test the AI with the command "java PlayTickTacToe X -test &#60; .&#47;Debug&#47;allTestCases.txt"