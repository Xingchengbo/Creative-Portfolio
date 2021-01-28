
public class drawCardAction implements Action {
	
	Deck deck;
	
	public drawCardAction(Deck deck)
	{
		this.deck = deck;
	}
	
	@Override
	public void doAction(Player curPlayer) {
		//Draw a card from the deck, get its action, then do it to the current player
		deck.drawCard().getAction().doAction(curPlayer);
		
	}

}
