import java.util.Scanner;
public class Main
{
	public static void play(Tile aiPlayer, boolean cliTest)
	{
		int scenareo = 1;
		Scanner input = new Scanner(System.in);	
		EnemyPlayer ai = new EnemyPlayer(aiPlayer);
		int x,y,cliInput;
		String playAgain;
		boolean successfulMove;
		boolean keepLooping = true, impossibleMove;

		while(keepLooping)
		{
			if(!cliTest)
				System.out.println(ai);
			
			if(aiPlayer == Tile.X)
			{
				try
				{
					ai.makeOptimalMove();
				}
				catch(Exception e)
				{
					System.out.println("Error in scenareo " + scenareo);
				}
				if(!cliTest)
					System.out.println(ai);

				if(ai.winner()!=Tile.Empty)
				{
					keepLooping = false;
					break;
				}

				do
				{
					impossibleMove = false;

					if(cliTest)
					{
						cliInput = input.nextInt();
						x = cliInput/3;
						y = cliInput%3;
					}
					else
					{
						x = input.nextInt();
						y = input.nextInt();
					}

					if(!ai.playerMakeMove(x,y))
					{
						impossibleMove=true;

						if(!cliTest)
							System.out.println("Cannot move there!");
					}
				}
				while(impossibleMove);

			}
			else
			{
				do
				{
					impossibleMove=false;

					if(cliTest)
					{
						cliInput = input.nextInt();
						x = cliInput/3;
						y = cliInput%3;
					}
					else
					{
						x = input.nextInt();
						y = input.nextInt();
					}

					if(!ai.playerMakeMove(x,y))
					{
						impossibleMove=true;

						if(!cliTest)
							System.out.println("Cannot move there!");
					}
				}
				while(impossibleMove);

				if(!cliTest)
					System.out.println(ai);

				if(ai.winner()!=Tile.Empty)
				{
					keepLooping = false;
					break;
				}

				ai.makeOptimalMove();
			}

			if(ai.winner()!=Tile.Empty)
				keepLooping = false;

			else if(ai.winner() != aiPlayer && ai.winner() != Tile.Tie && ai.winner() != Tile.Empty)
			{
				if(cliTest)
					System.out.println("Failed on scenareo " + scenareo + " "+ai.winner());
			}

			scenareo++;


		
		}

		if(!cliTest)
			System.out.println(ai);
	}


	public static void main(String [] args)
	{

		if(args.length<1)
		{
			System.out.println("Valid command line arguments: \"X\" or \"O\"");
			return;
		}

		boolean cliTest = false;

		if(args.length>1 && args[1].equals("-c"))
		{
			cliTest = true;
		}

		if(args[0].equals("X"))
		{
			play(Tile.O, cliTest);
		}
		else if(args[0].equals("O"))
		{			
			play(Tile.X, cliTest);
		}
		else
		{
			System.out.println("Valid command line arguments: \"X\" or \"O\"");
		}
	}
}