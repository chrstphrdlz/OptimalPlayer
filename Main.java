import java.util.Scanner;
public class Main
{
	public static void play(Tile aiPlayer, boolean testMode)
	{
		Scanner input = new Scanner(System.in);	
		EnemyPlayer ai = new EnemyPlayer(aiPlayer);
		int x,y,cliInput,
			numReads = 0, numScenareosRan = 0, failedScenareos = 0, numWins = 0, numTies = 0;
		boolean stillPlaying = true, unsucessfulMove, playAnotherGame = true;	
		while(playAnotherGame)
		{
			//exit if there is no more input
			if(testMode && !input.hasNextInt())
			{
				break;
			}

			while(stillPlaying)
			{
				//only print game if not in test mode
				if(!testMode)
					System.out.println(ai);
				
				//make x go first
				if(aiPlayer == Tile.X)
				{
					ai.makeOptimalMove();

					//print the resulting game if not in test mode
					if(!testMode)
						System.out.println(ai);

					//if there is a valid winner, stop
					if(ai.winner()!=Tile.Empty)
					{
						stillPlaying = false;
						break;
					}

					do
					{
						unsucessfulMove = false;

						//in test mode, a single number (0-9) 
						//represents a tile
						if(testMode)
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
							unsucessfulMove=true;

							if(!testMode)
								System.out.println("Cannot move there!");
						}
					}
					//keep looping if it is not a sucessful move
					while(unsucessfulMove);

				}
				else
				{
					do
					{
						unsucessfulMove=false;

						//in test mode, a single number (0-9) 
						//represents a tile
						if(testMode)
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
							unsucessfulMove=true;

							if(!testMode)
								System.out.println("Cannot move there!");
						}
					}
					//keep looping for unsucessful move
					while(unsucessfulMove);

					//print the resulting game if not in test mode
					if(!testMode)
						System.out.println(ai);

					if(ai.winner()!=Tile.Empty)
					{
						stillPlaying = false;
						break;
					}

					ai.makeOptimalMove();
				}				
			}

			Tile winner = ai.winner();

			//not playing anymore if there is a winner		
			stillPlaying = false;

			//the winner is the ai type
			if(winner == aiPlayer)
			{
				numWins++;
			}
			//the result is a tie
			else if(winner == Tile.Tie)
			{
				numTies++;
			}
			//the winner is the opposing player
			else
			{
				failedScenareos++;

				if(testMode)
					System.out.println("Failed on scenareo " + numScenareosRan + " "+ai.winner());
			}

			//will read in the rest of the permutaion and reset numReads
			if(testMode && numReads < 9)
				input.nextLine();

			//reset if we are doing a test
			if(testMode)
			{
				stillPlaying = true;
				numReads = 0;
				ai.reset();
				numScenareosRan++;
			}
			//if not, we are done
			else
			{
				playAnotherGame = false;
			}
		}

		//print test results
		if(testMode)
			System.out.println(numScenareosRan + " scenareos ran " 
				+ failedScenareos + " scenareos failed " 
				+ numWins + " scenareos won " 
				+ numTies + " scenareos tied");
		//print final game
		else
			System.out.println(ai);
	}


	public static void main(String [] args)
	{
		boolean testMode = false;

		if(args.length < 1 || args[0].equals("-help"))
		{
			System.out.println("usage: Main {X|O} [-test] ");
			return;
		}

		if(args.length>1 && args[1].equals("-test"))
		{
			testMode = true;
		}



		if(args[0].equals("X"))
		{
			play(Tile.O, testMode);
		}
		else if(args[0].equals("O"))
		{			
			play(Tile.X, testMode);
		}
		else
		{
			System.out.println("usage: Main {X|O} [-test] ");
		}
	}
}