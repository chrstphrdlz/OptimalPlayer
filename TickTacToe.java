//represents a tick tac toe game
class TickTacToe
{
	private Tile tiles[][];
	private int numMoves;;

	public TickTacToe()
	{
		numMoves = 0;
		tiles = new Tile[3][3];
		int i,j;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				tiles[i][j] = Tile.Empty;
	}

	//generates a game by taking in the index that the game is associated with it
	//the board can be converted to an int by considering each tile in board as a
	//base 3 number (0 = empty, 1 = X, 2 = O)
	public TickTacToe(int possibleGame)
	{
		numMoves = 0;
		tiles = new Tile[3][3];
		int i,j;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				tiles[i][j] = Tile.Empty;

		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
			{
				switch(possibleGame%3)
				{
					case 1:
						tiles[i][j] = Tile.X;
						break;
					case 2:
						tiles[i][j] = Tile.O;
						break;
					default:
						break;
				}
				possibleGame/=3;
			}	
	}

	// the state of the board expressed as an integer
	public int findPlaceInAllPossibleGames()
	{
		int returner = 0;
		int i, j;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
			{
				int  place = (int)Math.pow(3,j+3*i);
				switch(this.getTile(i,j))
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
			}
		return returner;
	}

	//create a new board and give it the same values	
	public TickTacToe copy()
	{
		TickTacToe returner = new TickTacToe();
		returner.numMoves = this.numMoves;
		int i,j;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				returner.tiles[i][j] = this.tiles[i][j];
		return returner;
	}

	public Tile getTile(int i, int j)
	{
		return this.tiles[i][j];
	}
	public int getNumMoves()
	{
		return numMoves;
	}

	//sees if the tile can be moved on
	public boolean canMove(int x, int y)
	{
		return tiles[y][x]==Tile.Empty;
	}

	//all will return a winner, or nothing (no winner)
	private Tile WinRow(int rowNum)
	{
		if(tiles[rowNum][0] == tiles[rowNum][1] && tiles[rowNum][0] == tiles[rowNum][2])
		{
			return tiles[rowNum][0];
		}
		else
		{
			return Tile.Empty;
		}
	}

	private Tile WinCol(int colNum)
	{
		if(tiles[0][colNum] == tiles[1][colNum] && tiles[0][colNum] == tiles[2][colNum])
		{
			return tiles[0][colNum];
		}
		else
		{
			return Tile.Empty;
		}
	}

	private Tile WinDiag()
	{
		if(tiles[0][0] == tiles[1][1] && tiles[0][0] == tiles[2][2])
		{
			return tiles[0][0];
		}
		else if(tiles[0][2] == tiles[1][1] && tiles[0][2] == tiles[2][0])
		{
			return tiles[0][2];
		}
		else
		{
			return Tile.Empty;
		}
	}

	//finds the winner by checking if there are any 
	//winning rows, columns, or diagonal winners
	public Tile Winner()
	{
		int i;
		Tile returner = Tile.Empty;
		for(i=0;i<3;i++)
		{
			returner = WinCol(i);
			if(returner!=Tile.Empty)
			{
				return returner;
			}

			returner = WinRow(i);
			if(returner!=Tile.Empty)
			{
				return returner;
			}
		}

		returner = WinDiag();

		if(returner == Tile.Empty && outOfTiles())
			return Tile.Tie;

		return returner;
	}

	//makes string representation of the board
	public String toString()
	{
		String returner = "";
		returner+=tiles[0][0]+" | "+tiles[0][1]+" | "+tiles[0][2]+"\n";
		returner+="---------"+"\n";
		returner+=tiles[1][0]+" | "+tiles[1][1]+" | "+tiles[1][2]+"\n";
		returner+="---------"+"\n";
		returner+=tiles[2][0]+" | "+tiles[2][1]+" | "+tiles[2][2]+"\n";
		return returner;
	}

	public boolean outOfTiles()
	{
		return numMoves == 9;
	}

	public boolean makeMove(int x, int y, Tile player)
	{
		if(tiles[y][x] != Tile.Empty)
		{
			//System.out.println("CANNOT MOVE THERE");
			return false;
		}
		else
		{
			tiles[y][x] = player;
			numMoves++;
			return true;
		}
	}

	public boolean eraseMove(int x, int y)
	{
		if(tiles[y][x] == Tile.Empty)
		{
			System.out.println("CANNOT REMOVE");
			return false;
		}
		else
		{
			tiles[y][x] = Tile.Empty;
			numMoves--;
			return true;
		}
	}
}