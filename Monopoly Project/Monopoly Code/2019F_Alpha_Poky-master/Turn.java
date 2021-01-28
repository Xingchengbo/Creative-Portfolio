import java.io.File;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Turn {

	Player[] players; 
	Player currentPlayer; 
	Random rand = new Random();
	Board gameBoard;
	int doubles;
	int roll1;
	int roll2;
	Deck chance;
	Deck commChest;
	File soundFile = new File("/Art/diceEDITED.wav");
	
	public Turn(Board board, int playerCount)
	{
		players = new Player[playerCount];

		for(int i = 0; i < playerCount; i++)
		{
			players[i] = new Player(i);
		}
		
		currentPlayer = players[0]; //On start, get first player
		gameBoard = board;
	}
	

	/** Make a deal between the current player and another player of their choice.
	 * The chosen player is decided from a pop-up dialog, and the dealing player may counter-offer or refuse.*/
	public static void makeDeal(Player otherPlayer)
	{
		//popupWindow for offer
		//Various options for trading owned spaces, money, etc.
		
		
		//int money;
		//Space[] spaces;
		/*
		 * 
		dealWindow(); //Look up more graphics
		if() //Trading money
		{
			changeMoney(currentPlayer, money);
			changeMoney(otherPlayer, money);
		}
		if() //Trading spaces
		{
			changeOwnedSpaces(currentPlayer, spaces)
			changeOwnedSpaces(otherPlayer, spaces)
		}
		
		*/
	}
	
	

	/** Iterate through a single game turn. The current player is tracked internally, using nextPlayer() to change players.*/
	public int takeTurn(int doubles)
	{
		
		this.doubles = doubles;
		
		JFrame frame = new JFrame();
		
		//Pop-up to notify next player their turn has begun
		JOptionPane.showMessageDialog(frame, "Player " + (currentPlayer.getID()+1) + ", it is your turn."); 
		int deal = -1;
		JOptionPane.showConfirmDialog(frame, "Would you like to deal?");
		
		//TODO Player interaction
		//OfferDealPopup(); //Pop-up to ask player if they want to deal
		
		if (deal != -1) //-1 signifies that a deal is not to be made
		{
			makeDeal(players[deal]);
		}
		
		boolean turnOver = jailFunc(frame);
		
		if (turnOver == false)
			{
			rollDiceAndMove(); //Do a normal turn
			}
		
		return this.doubles;

	}
	
	public void doAction()
	{
		Action action = gameBoard.getSpace(currentPlayer.getSpace()).getAction();
		action.doAction(currentPlayer);
	}
	
	
	/** Dice roll and movement action. Separated so jail check calls it (if not in jail)*/
	private void rollDiceAndMove()
	{
		Action action;
		//Roll the dice (2d6)
		roll1 = (rand.nextInt(6) + 1);
		roll2 = (rand.nextInt(6) + 1);
		
		if(roll1 == roll2)
		{
			doubles++;
			if(doubles == 3)
			{
				doubles = 0;
				action = new GoToAction(gameBoard.getSpace(40)); //GOTO Jail
			}
			else
			{
				int rollSum = roll1 + roll2;
				//Move the player
				//action = new AdvanceToAction(roll);
				action = new AdvanceToAction(rollSum);
				action.doAction(currentPlayer);
			}
		}
		else
		{
			doubles = 0;
			int rollSum = roll1 + roll2;
			//Move the player
			//action = new AdvanceToAction(roll);
			action = new AdvanceToAction(rollSum);
			action.doAction(currentPlayer);
		}


	}
	
	/** Jail processing function; calls rollDiceAndMoive if not in jail
	 * @param frame */
	private boolean jailFunc(JFrame frame)
	{
		if(currentPlayer.getSpace() == 40)//If in Jail
		{
			if(currentPlayer.getJail() == 3) //If enough time spent in jail
			{
				currentPlayer.setSpace(10);
				currentPlayer.setJail(0);
				return false;
			}
			else //Use GooJF card, pay money, or roll
			{

				if(currentPlayer.getGooj() == true)
				{
					JOptionPane.showMessageDialog(frame, "Would you like to use your 'Get Out of Jail Free' card?");
					//Add some if statement for using the card
				}

				roll1 = rand.nextInt(5)+1; //Roll in jail
				roll2 = rand.nextInt(5)+1;
				
				if(roll1 == roll2) //Rolled doubles
				{
					currentPlayer.setSpace(10 + (roll1*2));
					currentPlayer.setJail(0);
					return true;
				}
				
				else //Lose a turn
				{
					currentPlayer.setJail(currentPlayer.getJail() + 1);
					return true;
				}
				
			}
		}
		else //Not in jail
		{
			return false;
		}
	}
	
	/** Set the next player in line as the current player.*/
	public void nextPlayer()
	{
		//Modulo to wrap around back to Player 1
		currentPlayer = players[(currentPlayer.getID() + 1) % players.length];
	}
	
	public int getMoney(int playerNum)
	{
		return players[playerNum].getMoney();
	}
	
	public int getRoll()
	{
		return roll1 + roll2;
	}
}
