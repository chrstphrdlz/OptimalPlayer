class EnemyPlayer
{
	Tile aiType;

	Tile userType;

	DecisionTreeNode allPossibleNodes[];

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

		allPossibleNodes = new DecisionTreeNode[19683];
		for(int i=0;i<19683;i++)
			allPossibleNodes[i] = null;
		//winLossOfNodes = new int[19683];

		TickTacToe game = new TickTacToe();

		// because X is making the first move 
		allPossibleNodes[0] = makeTree(game, Tile.X,0);
	}

	private DecisionTreeNode makeTree(TickTacToe game, Tile player, int numMoves)
	{
		int indexOfGame = game.findPlaceInAllPossibleGames();
		
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

							int indexInStsticArray = game.findPlaceInAllPossibleGames();
							
							if(this.allPossibleNodes[indexInStsticArray]==null)
							{
								this.allPossibleNodes[indexInStsticArray] = makeTree(game,nextPlayer,numMoves+1);
								childNodes[indexOfNext] = this.allPossibleNodes[indexInStsticArray];
							}
							else
							{
								childNodes[indexOfNext] = this.allPossibleNodes[indexInStsticArray];
							}
							game.eraseMove(i,j);
							winLoss+=childNodes[indexOfNext].winLoss;
							
							//if in this next move the opponet wins, 
							//set the winner as the next player
							/*if(this.aiType == player && 
								childNodes[indexOfNext].winner==nextPlayer)
								allPossibleNodes[indexOfGame].winner = nextPlayer;
							*/
							
							indexOfNext++;
						}
						else
						{
							
						}
					}
				int numLoses = 0;

				for(i=0;i<childNodes.length;i++)
				{
					if(childNodes[i].winner == player)
					{	
						winner = player;
						break;
					}

					if(childNodes[i].winner == nextPlayer)
						numLoses++;
				}

				if(numLoses == childNodes.length)
					winner = nextPlayer;
			}
			//if a winner is determined, calculate the values for winloss
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

		this.allPossibleNodes[indexOfGame].winner = winner;
		allPossibleNodes[indexOfGame].winLoss = winLoss;
		allPossibleNodes[indexOfGame].nextNode = childNodes;

		return allPossibleNodes[indexOfGame];
	}

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
						maxIndex = moveToCheck.game.findPlaceInAllPossibleGames();

						max = moveToCheck.winLoss;
					}
				}	
				else
				{
					maxIndex = moveToCheck.game.findPlaceInAllPossibleGames();

					max = moveToCheck.winLoss;

					hasWinner = true;
				}

			}
			else
			{
				if(!hasWinner && moveToCheck.winLoss >= max)
				{
					maxIndex = moveToCheck.game.findPlaceInAllPossibleGames();

					max = moveToCheck.winLoss;
				}
			}
		}

		this.currentIndex = maxIndex;

		if(this.currentIndex == -1)
		{
			this.allPossibleNodes[0] = this.allPossibleNodes[-1];
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
		return this.allPossibleNodes[currentIndex].game;
	}

	public String toString()
	{
		return this.allPossibleNodes[currentIndex].game.toString();
	}

	public Tile winner()
	{
		return this.allPossibleNodes[currentIndex].game.Winner();
	}

	public void reset()
	{
		this.currentIndex = 0;
	}


}