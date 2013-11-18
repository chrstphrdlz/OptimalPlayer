import java.util.Scanner;
public class OptimalTickTackToe
{
	public static void playAsO()
	{
		Tile.X.winVal = 1;
		Tile.O.winVal = 1;
		int x,y;
		Scanner input = new Scanner(System.in);	
		TickTackToe game = new TickTackToe();
		TTTDecisionTree myTree = new TTTDecisionTree(game,Tile.O);
		System.out.println(myTree.game);
		TTTDecisionTree temp;



			myTree = myTree.makeMaxMove();
			System.out.println(myTree.game);
			//System.out.println(myTree.game.Winner());
			if(myTree.game.Winner()!=Tile.Empty)
			{
				System.out.println("Winner is "+myTree.game.Winner());
				return;
			}


		while(myTree.gameNotDone())
		{
			x = input.nextInt();
			y = input.nextInt();
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
				System.out.println("Winner is "+myTree.game.Winner());
				return;
			}

			myTree = myTree.makeMaxMove();
			System.out.println(myTree.game);
		}
	}
	public static void playAsX()
	{
		int x,y;
		Scanner input = new Scanner(System.in);	
		TickTackToe game = new TickTackToe();
		TTTDecisionTree myTree = new TTTDecisionTree(game,Tile.O);
		System.out.println(myTree.game);
		TTTDecisionTree temp;
		
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

			if(myTree.game.Winner()!=Tile.Empty)
			{
				System.out.println("Winner is "+myTree.game.Winner());
				return;
			}

			System.out.println(myTree.game);


			myTree = myTree.makeMaxMove();
			System.out.println(myTree.game);
			//System.out.println(myTree.game.Winner());
			if(myTree.game.Winner()!=Tile.Empty)
			{
				System.out.println("Winner is "+myTree.game.Winner());
				return;
			}
		}
	}
	public static void main(String [] args)
	{
		if(args.length!=1)
		{
			System.out.println("Valid command line arguments: \"X\" or \"O\"");
		}

		else if(args[0].equals("X"))
		{
			playAsX();
		}
		else if(args[0].equals("O"))
		{
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
		//System.out.println(returner);
		//System.out.println(numMoves);
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
	X(-1),O(1),Empty(0),Tie(0);
	int winVal;
	private Tile(int a)
	{
		this.winVal = a;
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
	int level;
	Tile winner;
	int numXWin[];
	int numOWin[];
	int numTies[];
	int winLoss;
	int movex, movey;
	Tile currentPlayer;
	TickTackToe game;
	Tile aiType;
	private TTTDecisionTree nextNode[];

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
		//System.out.println("length = "+nextNode.length);
		for(i=0;i<nextNode.length;i++)
		{
			//finds the node with the largest win/lose points
			//as long as it is not a losing branch
			//printNextGame(i);
			//System.out.println("currentPlayer = " + currentPlayer);

			if(nextNode[i].winLoss>max && nextNode[i].winner!=currentPlayer)
			{
				//printNextGame(i);
				//System.out.println("CHOSEN");
				//System.out.println("\n\n\n\n\n\n\n\n");
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
			if(nextNode[i].currentPlayer == player && nextNode[i].movex == x && nextNode[i].movey == y)
				return i;
		System.out.println("Error finding move");
		return -1;
	}

	public void printNextGame(int i)
	{
		TickTackToe printer = game.copy();
		printer.makeMove(this.nextNode[i].movex,this.nextNode[i].movey,this.nextNode[i].currentPlayer);
		System.out.println("Winner is " + this.nextNode[i].winner);
		System.out.println("Winloss = " + this.nextNode[i].winLoss);
		System.out.println(printer);
	}

	public TTTDecisionTree(TickTackToe game, Tile player)
	{
		this(game, player,0,0, player);
	}

	public TTTDecisionTree(TickTackToe game, Tile player,int movex, int movey, Tile opponetType)
	{
		this.aiType = opponetType;
		this.game = game.copy();
		currentPlayer = player;
		this.movex = movex;
		this.movey = movey;
		int i,j,indexOfNext = 0;
		Tile nextPlayer;
		level = game.getNumMoves();
		if(!game.outOfTiles())
		{
			winner =  game.Winner();
			//there is not a winner!
			if(Tile.Empty ==  winner)
			{
				//dfs

				numXWin = new int[9-level];
				numOWin = new int[9-level];
				numTies = new int[9-level];
				nextNode = new TTTDecisionTree[9-level];

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
						if(game.canMove(i,j))
						{
							game.makeMove(i,j,nextPlayer);
							this.nextNode[indexOfNext] = new TTTDecisionTree(game,nextPlayer,i,j, opponetType);
							
							game.eraseMove(i,j);
							winLoss+=this.nextNode[indexOfNext].winLoss;
							if(currentPlayer != aiType)
							{
								if(this.nextNode[indexOfNext].winner==nextPlayer)
									this.winner = nextPlayer;
								
							}
							else
							{
								if(this.nextNode[indexOfNext].winner==nextPlayer)
									this.winner = nextPlayer;
							}
							indexOfNext++;
							//if(this.nextNode[indexOfNext].winner!=Tile.Tie && )
						}
					}
				//for(i=0;i<nextNode.length;i++)
				//System.out.println(nextNode[i].winner);
				//System.out.println(maxMove());
			}
			else
			{
				winner =  game.Winner();
				winLoss = factorial(9-level)*winner.winVal;
			}
		}
		else
		{
			winner =  game.Winner();
			winLoss = factorial(9-level)*winner.winVal;
		}

		//if(this.winner == Tile.Empty)
		//	System.out.println(this.game);
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