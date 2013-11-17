import java.util.Scanner;
public class OptimalTickTackToe
{
	public static void main(String [] args)
	{
		if(args.length!=1)
			return;

		Tile me = Tile.X;
		int x,y;

		Scanner input = new Scanner(System.in);
		Tile opponet = Tile.O;
		TickTackToe game = new TickTackToe();
		TTTDecisionTree myTree = new TTTDecisionTree(game,Tile.O,0,0);
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
		//System.out.println("\n\nwinner"+game.Winner());

	}
}

class TickTackToe
{
	private Tile tiles[][];
	private int numMoves;
	int weight;

	public TickTackToe()
	{
		numMoves = 0;
		tiles = new Tile[3][3];
		int i,j;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				tiles[i][j] = Tile.Empty;
		weight = 0;
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
	X(-1),O(1),Empty(-200000),Tie(0);
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

	public boolean losesNextTurn()
	{
		int i;
		if(nextNode.length == 0)
			return false;
		for(i=0;i<nextNode.length;i++)
			if(nextNode[i].winner != Tile.Empty && nextNode[i].winner != Tile.Tie)
				return true;
		return false;
	}

	public int indexOfMaxMove()
	{
		int max=-20000,i,index=-1;
		for(i=0;i<nextNode.length;i++)
			if(nextNode[i].winLoss>max && !nextNode[i].losesNextTurn())
			{
				max = nextNode[i].winLoss;
				index = i;

			}

		return index;
	}

	public int maxMove()
	{
		int max=-20000,i,movexmax=-1000,moveymax=-1000;
		for(i=0;i<nextNode.length;i++)
			if(nextNode[i].winLoss>max)
			{
				max = nextNode[i].winLoss;
				movexmax = nextNode[i].movex;
				moveymax = nextNode[i].movey;

			}

			System.out.println("max x = "+movexmax);
			System.out.println("max y = "+moveymax);
		return max;
	}

	public int findIndexOfMove(int x, int y, Tile player)
	{
		int i;
		for(i=0;i<nextNode.length;i++)
			if(nextNode[i].currentPlayer == player && nextNode[i].movex == x && nextNode[i].movey == y)
				return i;
		System.out.println("Nothing");
		System.out.println(nextNode.length);
		return -1;
	}

	public TTTDecisionTree(TickTackToe game, Tile player,int movex, int movey)
	{
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
							this.nextNode[indexOfNext] = new TTTDecisionTree(game,nextPlayer,i,j);
							game.eraseMove(i,j);
							winLoss+=this.nextNode[indexOfNext].winLoss;
							indexOfNext++;
						}
					}
				//System.out.println(maxMove());
			}
			else
			{
				winLoss = factorial(9-level)*winner.winVal;
			}
		}
		else
		{
			winner =  game.Winner();
			winLoss = factorial(9-level)*winner.winVal;
		}
	}
	private int factorial(int num)
	{
		if(num<2)
			return 1;
		else return num * factorial(num-1);
	}
}