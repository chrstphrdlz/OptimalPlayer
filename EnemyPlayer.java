
class EnemyPlayer
{
	Tile aiType;
	Tile userType;

	//the array representing all the possible decision tree nodes
	DecisionTreeNode allPossibleNodes[];

	//the index in allPossibleNodes which matches the state of the current game
	int currentIndex;

	EnemyPlayer(Tile aiType)
	{
		if(aiType==Tile.O)
			Tile.makeOwin();
		else
			Tile.makeXwin();			

		currentIndex = 0;

		this.aiType = aiType;

		if(aiType == Tile.X)
		{
			this.userType = Tile.O;
		}
		else if(aiType == Tile.O)
		{
			this.userType = Tile.X;
		}
		else
		{
			System.out.println("Error making ai");
		}
		//a maximum of 3^9 different boards (not all will be used)
		allPossibleNodes = new DecisionTreeNode[19683];
		for(int i=0;i<19683;i++)
			allPossibleNodes[i] = null;

		//initialize an empty board
		TickTacToe game = new TickTacToe();
		//start creating the possible combinations
		//because X is making the first move player is X
		allPossibleNodes[0] = makeTree(game, Tile.X,0);
	}

	private DecisionTreeNode makeTree(TickTacToe game, Tile player, int numMoves)
	{
		int indexOfGame = game.findPlaceInAllPossibleGames();
		
		//if it is null, create a new node at this location
		if(allPossibleNodes[indexOfGame] == null)
			allPossibleNodes[indexOfGame] = new DecisionTreeNode(game, player);
		
		Tile nextPlayer = Tile.Empty;

		if(player == Tile.X)
			nextPlayer = Tile.O;
		else if(player == Tile.O)
			nextPlayer = Tile.X;
		else
			System.out.println("Error");

		int i,j,indexOfNext = 0, winLoss;

		Tile winner;
		//the possible decisions that can be made at this state in the game
		DecisionTreeNode childNodes[] = new DecisionTreeNode[9-numMoves];

		//if there are no avalible tiles to move on
		if(!game.outOfTiles())
		{
			winner =  game.Winner();

			//no winner determined
			if(Tile.Empty ==  winner)
			{
				winLoss = 0;

				for(i=0;i<3;i++)
					for(j=0;j<3;j++)
					{
						//make all possible avalible moves
						if(game.canMove(i,j))
						{
							game.makeMove(i,j,player);

							//represents the place in allPossibleNodes for the game with this move
							int indexInStaticArray = game.findPlaceInAllPossibleGames();
							
							//if it is null, make the tree for this game and set it as a child node
							//this prevents needing to re-compute already done nodes
							if(this.allPossibleNodes[indexInStaticArray]==null)
							{
								this.allPossibleNodes[indexInStaticArray] = makeTree(game,nextPlayer,numMoves+1);
								childNodes[indexOfNext] = this.allPossibleNodes[indexInStaticArray];
							}
							//if already made, set it as a child node
							else
							{
								childNodes[indexOfNext] = this.allPossibleNodes[indexInStaticArray];
							}
							//revert board, add winloss, and increment childNode index
							game.eraseMove(i,j);
							winLoss+=childNodes[indexOfNext].winLoss;							
							indexOfNext++;
						}
					}

				int numLoses = 0;

				for(i=0;i<childNodes.length;i++)
				{
					//if there is a possible winning move for the current player
					//then the winner is the current player
					if(childNodes[i].winner == player)
					{	
						winner = player;
						break;
					}

					if(childNodes[i].winner == nextPlayer)
						numLoses++;
				}

				//if all the child nodes are loses, this node's winner is the
				//next player (there is no oppourtunity at that point to not lose)
				if(numLoses == childNodes.length)
					winner = nextPlayer;
			}
			//if a winner is determined, calculate the values for winloss
			//the factorial is because a win higher up the tree should be considered
			//winning for all the nonexistant nodes below it
			else
			{
				winLoss = factorial(9-numMoves)*winner.winVal;
			}
		}
		//find the winner if no moves left
		else
		{
			winner =  game.Winner();
			winLoss = factorial(9-numMoves)*winner.winVal;
		}

		//sets all the values of this current node
		this.allPossibleNodes[indexOfGame].winner = winner;
		allPossibleNodes[indexOfGame].winLoss = winLoss;
		allPossibleNodes[indexOfGame].nextNode = childNodes;

		return allPossibleNodes[indexOfGame];
	}

	//used only for finding winloss
	int factorial(int i)
	{
		if(i<0)
		{
			System.out.println("Error in factorial");
		}

		if(i<2)
		{
			return 1;
		}

		int returner = 1;

		while(i>=2)
		{
			returner*=i;
			i--;
		}

		return returner;
	}

	public boolean playerMakeMove(int x, int y)
	{
		return makeMove(x, y, userType);
	}

	public void makeOptimalMove()
	{
		DecisionTreeNode possibleMoves[] = allPossibleNodes[currentIndex].nextNode;
		DecisionTreeNode moveToCheck = possibleMoves[0];
		int max = -100000000;
		int maxIndex = -1;
		boolean hasWinner = false;

		for(int i=0;i<possibleMoves.length;i++)
		{
			moveToCheck = possibleMoves[i];

			if(moveToCheck.winner == userType)
			{

			}
			else if(moveToCheck.winner == aiType)
			{
				if(hasWinner)
				{
					if(moveToCheck.winLoss > max)
					{
						maxIndex = moveToCheck.getNodeIndex();

						max = moveToCheck.winLoss;
					}
				}	
				else
				{
					maxIndex = moveToCheck.getNodeIndex();

					max = moveToCheck.winLoss;

					hasWinner = true;
				}

			}
			else
			{
				if(!hasWinner && moveToCheck.winLoss >= max)
				{
					maxIndex = moveToCheck.getNodeIndex();

					max = moveToCheck.winLoss;
				}
			}
		}

		this.currentIndex = maxIndex;
		//will pourposely crash the program in debug mode
		//(will only happen if there is no option but to lose)
		if(this.currentIndex == -1)
		{
			assert(false);
			this.currentIndex = 0;
		}
	}

	// will return true if the move is valid
	public boolean makeMove(int x, int y, Tile player)
	{
		// get the current game board
		TickTacToe currentGame = getCurrentGame();
		
		// make the move associated with it
		if(!currentGame.makeMove(x,y,player))
		{
			return false;
		}

		// the next game state has this index accociated with it
		int nextNodeIndex = currentGame.findPlaceInAllPossibleGames();

		// because the games are passed by reference, must revert to origional state
		currentGame.eraseMove(x,y);

		// change the associated index  
		this.currentIndex = nextNodeIndex;

		return true;
	}

	public TickTacToe getCurrentGame()
	{
		return this.allPossibleNodes[currentIndex].getGame();
	}

	public String toString()
	{
		return this.allPossibleNodes[currentIndex].getGame().toString();
	}

	public Tile winner()
	{
		return this.allPossibleNodes[currentIndex].getGame().Winner();
	}

	public void reset()
	{
		this.currentIndex = 0;
	}


}