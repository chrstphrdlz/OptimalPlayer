//represents a tile on the board
enum Tile
{
	//initiazlies them with no preference (0)
	X(0),O(0),Empty(0),Tie(0);

	//the winVal is what the ai is trying to maximize
	int winVal;

	private Tile(int a)
	{
		this.winVal = a;
	}

	//the positive winVal causes the ai to try to make that tile win
	//so if we want to make x win, we male X's winvalue positive
	//and vice versa
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

	//allows the individual tiles to be printed
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