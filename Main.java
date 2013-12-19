import java.util.Scanner;
public class Main
{
	public static void play(Tile aiPlayer, boolean cliTest)
	{
		int scenareo = 0;
		Scanner input = new Scanner(System.in);	
		EnemyPlayer ai = new EnemyPlayer(aiPlayer);
		int x,y,cliInput;
		String playAgain;
		boolean successfulMove;
		while(true)
		{
			while(true)
			{
				if(aiPlayer==Tile.X)
				{
					ai.makeOptimalMove();
				}

				if(ai.winner()!=Tile.Empty)
					break;

				if(!cliTest)
					System.out.println(ai);

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

				successfulMove = ai.playerMakeMove(x,y);

				while(!successfulMove)
				{
					if(!cliTest)
						System.out.println("Cannot move there!");					
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

					successfulMove = ai.playerMakeMove(x,y);
				}


				if(!cliTest)
					System.out.println(ai);

				if(ai.winner()!=Tile.Empty)
					break;
				if(aiPlayer!=Tile.X)
				{
					ai.makeOptimalMove();
				}
				if(ai.winner()!=Tile.Empty)
					break;
			}

			if(ai.winner()!=Tile.Tie && ai.winner()!=aiPlayer)
			{
				System.out.println("lose with scenareo "+ scenareo);
			}

			scenareo++;

			if(cliTest)
			{
				playAgain = input.nextLine();
				playAgain = input.next();
				if(!playAgain.equals("yes"))
				{
					return;	
				}
				else
				{
					ai.reset();
				}					
			}
			else
			{
				break;		
			}
		}



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

		if(args.length>1 && args[1].equals("c"))
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