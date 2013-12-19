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
	TickTacToe game;
	//the possible moves
	public DecisionTreeNode nextNode[];

	public DecisionTreeNode(TickTacToe game, Tile player)
	{
		//initializes all object primatives
		this.game = game.copy();
		this.currentPlayer = player;
		this.numMoves = game.getNumMoves();

	}

	public DecisionTreeNode getNextMove(int x, int y, Tile player)
	{
		return this.nextNode[findIndexOfMove(x,y,player)];
	}

	public DecisionTreeNode makeMaxMove()
	{
		return nextNode[indexOfMaxMove()];
	}

	public DecisionTreeNode makeMove(int x, int y, Tile player)
	{
		int index = findIndexOfMove(x,y,player);
		if(index==-1)
			return null;
		return nextNode[findIndexOfMove(x,y,player)];
	}

	public int indexOfMaxMove()
	{
		//This is right after the player moves,
		//So the current player is the one we want to keep from winning
		double max=Double.NEGATIVE_INFINITY;
		int i,index=-1;
		Tile nextPlayer = Tile.Empty;

		if(currentPlayer==Tile.X)
		{
			nextPlayer = Tile.O;
		}
		else if(currentPlayer == Tile.O)
		{
			nextPlayer = Tile.X;
		}
		else
		{
			System.out.println("ERROR");
		}

		for(i=0;i<nextNode.length;i++)
		{
			//finds the node with the largest win/lose points
			//as long as it is not a losing branch
			//System.out.println(nextNode[i].winLoss);
			if(nextNode[i].winLoss>max && nextNode[i].winner!=currentPlayer)
			{
				max = nextNode[i].winLoss;
				index = i;
			}
		}

		if(i==-1)
			System.out.println("ERROR FINDING INDEX");

		return index;
	}

	public int findIndexOfMove(int x, int y, Tile player)
	{
		int i;
		TickTacToe tempGame = this.game.copy();
		tempGame.makeMove(x,y,player);

		for(i=0;i<nextNode.length;i++)
		{
			TickTacToe possibleGame = nextNode[i].game;
			//System.out.println("Start");
			//System.out.println(possibleGame);
			//System.out.println(tempGame+"\n\n\n\n\n\n\n\n");
			if(tempGame.findPlaceInAllPossibleGames() == possibleGame.findPlaceInAllPossibleGames())
			{
				return i;				
			}		
		}
		System.out.println("Error finding move");
		return -1;
	}	

	private int factorial(int num)
	{
		if(num<2)
			return 1;
		else return num * factorial(num-1);
	}

	public boolean gameNotDone()
	{
		return this.game.Winner()==Tile.Empty;
	}

	private int findPlaceInAllPossibleGames(TickTacToe game)
	{
		int returner = 0;
		int i, j;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
			{
				int  place = (int)Math.pow(3,j+3*i);
				switch(game.getTile(i,j))
				{
					case O:
						returner+=place;
						break;
					case X:
						returner+=2*place;
						break;
					default:
						break;
				}
				//System.out.println(returner);
				
			}
		return returner;
	}
}