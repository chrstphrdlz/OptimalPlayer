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

## EnemyPlayer ##
## DecisionTreeNode ##
## TickTacToe ##
## Tile ##
This tile enumeration represents one of the 9 squares in the Tick Tack Toe board. It also represents the types of winning (either a tile type: X, O, or nothing, or a tie). It has a toString method to allow each individual tile to print on the game board. The winVal associated with each tile is what is attempting to be maximized (if the ai is playing as X, we want to make X positive and O negative). For example, if we want to make X win, we call that method and it makes the X winVal 1 and O -1.
## PlayTickTacToe ##