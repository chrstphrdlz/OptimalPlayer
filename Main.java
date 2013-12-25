import java.util.Scanner;
public class Main
{
	public static void play(Tile aiPlayer, boolean cliTest)
	{
		Scanner input = new Scanner(System.in);	
		EnemyPlayer ai = new EnemyPlayer(aiPlayer);
		int x,y,cliInput,numReads = 0, numScenareosRan = 0;
		boolean successfulMove;
		boolean stillPlaying = true, impossibleMove, playAnotherGame = true;
		while(playAnotherGame)
		{
			if(cliTest && !input.hasNextInt())
				break;

			while(stillPlaying)
			{
				if(!cliTest)
					System.out.println(ai);
				
				if(aiPlayer == Tile.X)
				{
					ai.makeOptimalMove();
					
					if(!cliTest)
						System.out.println(ai);

					if(ai.winner()!=Tile.Empty)
					{
						stillPlaying = false;
						break;
					}

					do
					{
						impossibleMove = false;

						if(cliTest)
						{
							numReads++;
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
							numReads++;
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
						stillPlaying = false;
						break;
					}
					try{
					ai.makeOptimalMove();}
					catch(Exception e)
					{
						System.out.println(numScenareosRan);
						System.out.println("Moved to " +x +" "+y);
					}
				}

				if(ai.winner()!=Tile.Empty)
					stillPlaying = false;


				else if(ai.winner() != aiPlayer && ai.winner() != Tile.Tie && ai.winner() != Tile.Empty)
				{
					if(cliTest)
						System.out.println("Failed on scenareo " + numScenareosRan + " "+ai.winner());
				}		
			}

			//will read in the rest of the permutaion and reset numReads
			if(cliTest && numReads < 9)
				input.nextLine();

			numReads = 0;
			ai.reset();
			stillPlaying = true;
			numScenareosRan++;
		}

		if(cliTest)
			System.out.println(numScenareosRan + " scenareos ran");
		else
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