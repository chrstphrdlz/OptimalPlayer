all: TTT

TTT: EnemyPlayer.java DecisionTreeNode.java TickTacToe.java Tile.java Main.java

	javac EnemyPlayer.java DecisionTreeNode.java TickTacToe.java Tile.java Main.java


clean:
	rm *.class