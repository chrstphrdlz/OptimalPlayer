class DecisionTreeNode
{
	//total number of moves so far
	int numMoves;
	//the win/loss points accumulated 
	int winLoss;
	Tile currentPlayer;
	//the tile type that is winning (Empty if no one wins this branch)
	Tile winner;
	//the tick tack toe game being referenced in this move
	private TickTacToe game;
	//the possible moves
	public DecisionTreeNode nextNode[];

	public DecisionTreeNode(TickTacToe game, Tile player)
	{
		//initializes all object primatives
		this.game = game.copy();
		this.winner = game.Winner();
		this.currentPlayer = player;
		this.numMoves = game.getNumMoves();

	}

	//finds the transform of the current game and finds the index
	public int getNodeIndex()
	{
		return game.findPlaceInAllPossibleGames();
	}	

	public boolean gameNotDone()
	{
		return this.game.Winner()==Tile.Empty;
	}

	public TickTacToe getGame()
	{
		return this.game.copy();
	}

}