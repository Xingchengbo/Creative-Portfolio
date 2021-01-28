import java.util.ArrayList;
import java.util.Random;

public class Deck {

	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> discard = new ArrayList<Card>();
	//private Player[] players;
	
	/** Create a deck; 0 for Chance, any other int for Com Chest*/
	public Deck(int deckType, Space[] spaces)
	{
		//this.players = players;
		if (deckType == 0)
		{
			createChanceDeck(spaces);
			for(int i = 0; i < deck.size(); i++)
			{
				discard.add(deck.remove(0));
			}
			shuffle();
		}
		else
		{
			createCommDeck(spaces);
			for(int i = 0; i < deck.size(); i++)
			{
				discard.add(deck.remove(0));
			}
			shuffle();
		}
	}

	//TODO look up Comm cards
	private void createCommDeck(Space[] spaces) {
		deck.add(new Card(new AdvanceToAction(-1), "Advance to Go", "Collect $200."));
		deck.add(new Card(new BankPaymentAction(200), "Bank Error in Your Favor", "Collect $200."));
		deck.add(new Card(new BankPaymentAction(-50), "Doctor's Fees", "Pay $50."));
		deck.add(new Card(new BankPaymentAction(50), "Sale of Stock", "From sale of stock you collect $50."));
		deck.add(new Card(new GetOutOfJailFreeAction(), "Get Out of Jail Free", "This card may be kept until needed or sold/traded."));
		deck.add(new Card(new GoToAction(spaces[40]), "Go to Jail", "Go directly to jail. Do not pass Go, do not collect $200."));
		//deck.add(new Card(new PayPlayersAction(50), "Grand Opera Night", "Collect $50 from every player for opening night seats."));
		deck.add(new Card(new BankPaymentAction(100), "Holiday Fund Matures", "Collect $100."));
		deck.add(new Card(new BankPaymentAction(20), "Income Tax Refund", "Collect $20."));
		//deck.add(new Card(new PayPlayersAction(10), "It's Your Birthday.", "Collect $10 from every player."));
		deck.add(new Card(new BankPaymentAction(100), "Life Insurance Matures", "Collect $100."));
		deck.add(new Card(new BankPaymentAction(-50), "Hospital Fees", "Pay $50."));
		deck.add(new Card(new BankPaymentAction(-50), "School Fees", "Pay $50."));
		deck.add(new Card(new BankPaymentAction(25), "Consultancy Fees", "Collect $25 consultancy fee."));
		//deck.add(new Card(new BankPaymentAction(), "You Are Assessed For Street Repairs", "Pay $40 per house and $115 per hotel you own."));
		deck.add(new Card(new BankPaymentAction(10), "You Have Won Second Prize in a Beauty Contest", "Collect $10."));
		deck.add(new Card(new BankPaymentAction(100), "You Inherit $100 ", "Collect $100."));
	}

	//TODO look up Chance cards
	private void createChanceDeck(Space[] spaces) {
		deck.add(new Card(new AdvanceToAction(-1), "Advance to Go", "Collect $200."));
		//deck.add(new Card(new GoToNearestAction(12, 28), "Advance Token to Nearest Utility", "If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total 10 times the amount thrown."));
		//deck.add(new Card(new GoToNearestAction(5, 15, 25, 35), "Advance Token to The Nearest Railroad", "Pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank."));
		deck.add(new Card(new BankPaymentAction(50), "Dividend Fees", "Bank pays you dividend of $50."));
		deck.add(new Card(new GetOutOfJailFreeAction(), "Get Out of Jail Free", "This card may be kept until needed or sold/traded."));
		deck.add(new Card(new AdvanceToAction(-3), "Go Back", "Go back three spaces."));
		deck.add(new Card(new GoToAction(spaces[40]), "Go to Jail", "Go directly to jail. Do not pass Go, do not collect $200."));
		//deck.add(new Card(new BankPaymentAction(), "Make General Repairs on All Your Property", "For each house pay $25, For each hotel pay $100."));
		deck.add(new Card(new AdvanceToAction(5), "Take a Trip to Kings Cross Station", "If you pass Go, collect $200."));
		//deck.add(new Card(new PayPlayersAction(-50), "You Have Been Elected Chairman of The Board", "Pay each player $50."));
		deck.add(new Card(new BankPaymentAction(150), "Your Building and Loan Matures", "Receive Collect $150."));
		deck.add(new Card(new BankPaymentAction(100), "You have won a crossword competition", "Collect $100."));
		deck.add(new Card(new BankPaymentAction(-15), "Pay Poor Tax", "Pay $15."));	
	}
	
	/** Shuffle discard back into main deck*/
	public void shuffle()
	{
		Random rand = new Random();
		for(int i = 0; discard.size() > i; i+= 0)
		{
			//Remove a random card from discard, and add it back into the deck (mimics shuffling)
			deck.add(discard.remove(rand.nextInt(discard.size())));
		}
	}
	
	/** Draw the top card of the deck*/
	public Card drawCard()
	{
		Card drawnCard = deck.remove(0);
		discard.add(drawnCard);
		return drawnCard;
	}
	
}
