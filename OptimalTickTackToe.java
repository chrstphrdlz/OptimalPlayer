import java.util.Scanner;
public class OptimalTickTackToe
{
	public static void playAsO()
	{
		//make X the winning tile to maximize
		int x,y;
		Scanner input = new Scanner(System.in);

		TTTDecisionTree myTree = new TTTDecisionTree(Tile.O);
		TTTDecisionTree temp;

		System.out.println(myTree.game);

		//AI makes the first move
		myTree = myTree.makeMaxMove();
		System.out.println(myTree.game);

		while(myTree.gameNotDone())
		{
			x = input.nextInt();
			y = input.nextInt();

			//keep getting user input until move is valid
			temp = myTree.makeMove(x,y,Tile.O);
			while(temp == null)
			{
				System.out.println("Cannot move there!");
				x = input.nextInt();
				y = input.nextInt();
				temp = myTree.makeMove(x,y,Tile.O);
			}

			myTree = temp;

			System.out.println(myTree.game);

			if(myTree.game.Winner()!=Tile.Empty)
			{
				break;
			}

			myTree = myTree.makeMaxMove();
			System.out.println(myTree.game);

			if(myTree.game.Winner()!=Tile.Empty)
			{
				break;
			}
		}

		System.out.println("Winner is "+myTree.game.Winner());
		return;
	}
	public static void playAsX()
	{
		int x,y;
		Scanner input = new Scanner(System.in);	

		TTTDecisionTree myTree = new TTTDecisionTree(Tile.O);		
		TTTDecisionTree temp;

		System.out.println(myTree.game);
		
		while(myTree.game.Winner()==Tile.Empty)
		{
			x = input.nextInt();
			y = input.nextInt();
			temp = myTree.makeMove(x,y,Tile.X);
			while(temp == null)
			{
				System.out.println("Cannot move there!");
				x = input.nextInt();
				y = input.nextInt();
				temp = myTree.makeMove(x,y,Tile.X);
			}

			myTree = temp;
			System.out.println(myTree.game);

			if(myTree.game.Winner()!=Tile.Empty)
			{
				break;
			}

			myTree = myTree.makeMaxMove();
			System.out.println(myTree.game);

			if(myTree.game.Winner()!=Tile.Empty)
			{
				break;
			}
		}

		System.out.println("Winner is "+myTree.game.Winner());
		return;
	}
	public static void main(String [] args)
	{
		if(args.length!=1)
		{
			System.out.println("Valid command line arguments: \"X\" or \"O\"");
		}

		else if(args[0].equals("X"))
		{
			Tile.makeOwin();
			playAsX();
		}
		else if(args[0].equals("O"))
		{			
			Tile.makeXwin();
			playAsO();
		}
		else
		{
			System.out.println("Valid command line arguments: \"X\" or \"O\"");
		}
	}
}

class TickTackToe
{
	private Tile tiles[][];
	private int numMoves;;

	public TickTackToe()
	{
		numMoves = 0;
		tiles = new Tile[3][3];
		int i,j;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				tiles[i][j] = Tile.Empty;
	}
	
	public TickTackToe copy()
	{
		TickTackToe returner = new TickTackToe();
		returner.numMoves = this.numMoves;
		int i,j;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				returner.tiles[i][j] = this.tiles[i][j];
		return returner;
	}

	public int getNumMoves()
	{
		return numMoves;
	}

	public boolean canMove(int x, int y)
	{
		return tiles[y][x]==Tile.Empty;
	}

	//Will return a winner, or nothing (no winner)
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

	public boolean outOfTiles()
	{
		return numMoves == 9;
	}

	public boolean makeMove(int x, int y, Tile player)
	{
		if(tiles[y][x] != Tile.Empty)
		{
			System.out.println("CANNOT MOVE THERE");
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

enum Tile
{
	X(0),O(0),Empty(0),Tie(0);
	int winVal;
	private Tile(int a)
	{
		this.winVal = a;
	}
	public static void makeXwin()
	{
		Tile.X.winVal = 1;
		Tile.O.winVal = -1;
	}
	public static void makeOwin()
	{
		Tile.O.winVal = 1;
		Tile.X.winVal = -1;
	}
	public String toString()
	{
		if(this == Tile.X)
			return "X";
		else if(this == Tile.O)
			return "O";
		else if(this == Tile.Empty)
			return " ";
		else
			return "TIE";
	}
}

class TTTDecisionTree
{
	//total number of moves so far
	int numMoves;
	//the win/loss points accumulated 
	int winLoss;
	//the coordinates of the tile placed this move
	int movex, movey;
	//the type of tile the AI is
	Tile aiType;
	//the tile type that is winning (Empty if no one wins this branch)
	Tile winner;
	//the tile currently being played
	Tile currentPlayer;
	//the tick tack toe game being referenced in this move
	TickTackToe game;
	//the possible moves
	private TTTDecisionTree nextNode[];

	public TTTDecisionTree(Tile playerType)
	{
		this(new TickTackToe(), playerType,0,0, playerType);
	}

	public TTTDecisionTree(TickTackToe game, Tile player)
	{
		this(game, player,0,0, player);
	}

	public TTTDecisionTree(TickTackToe game, Tile player,int movex, int movey, Tile opponetType)
	{
		//initializes all object primatives
		this.aiType = opponetType;
		this.game = game.copy();
		currentPlayer = player;
		this.movex = movex;
		this.movey = movey;
		numMoves = game.getNumMoves();

		int i,j,indexOfNext = 0;
		Tile nextPlayer;

		//if there are no avalible tiles to move on
		if(!game.outOfTiles())
		{
			winner =  game.Winner();

			//no winner determined
			if(Tile.Empty ==  winner)
			{
				nextNode = new TTTDecisionTree[9-numMoves];

				nextPlayer = Tile.Empty;

				if(player == Tile.X)
				{
					nextPlayer = Tile.O;
				}
				else if(player == Tile.O)
				{
					nextPlayer = Tile.X;
				}
				else
				{
					System.out.println("Error");
				}

				winLoss = 0;

				for(i=0;i<3;i++)
					for(j=0;j<3;j++)
					{
						//make all possible avalible moves
						if(game.canMove(i,j))
						{
							game.makeMove(i,j,nextPlayer);
							this.nextNode[indexOfNext] = new TTTDecisionTree(game,nextPlayer,i,j, opponetType);
							
							game.eraseMove(i,j);
							winLoss+=this.nextNode[indexOfNext].winLoss;
							
							//if in this next move the opponet wins, 
							//set the winner as the next player
							if(this.currentPlayer == aiType && 
								this.nextNode[indexOfNext].winner==nextPlayer)
								this.winner = nextPlayer;
							
							indexOfNext++;
						}
					}
				int numLoses = 0;
				if(this.currentPlayer != aiType)
					for(i=0;i<this.nextNode.length;i++)
					{
						if(this.nextNode[i].winner == this.currentPlayer)
							numLoses++;
					}
				if(numLoses == nextNode.length)
					this.winner = this.currentPlayer;
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
	}

	public TTTDecisionTree getNextMove(int x, int y, Tile player)
	{
		return this.nextNode[findIndexOfMove(x,y,player)];
	}

	public TTTDecisionTree makeMaxMove()
	{
		return nextNode[indexOfMaxMove()];
	}

	public TTTDecisionTree makeMove(int x, int y, Tile player)
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
		for(i=0;i<nextNode.length;i++)
			if(nextNode[i].currentPlayer == player && 
				nextNode[i].movex == x && 
				nextNode[i].movey == y)
					return i;
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
}