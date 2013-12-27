all: TTT

TTT: EnemyPlayer.java DecisionTreeNode.java TickTacToe.java Tile.java PlayTickTacToe.java

	javac EnemyPlayer.java DecisionTreeNode.java TickTacToe.java Tile.java PlayTickTacToe.java


clean:
	rm *.class