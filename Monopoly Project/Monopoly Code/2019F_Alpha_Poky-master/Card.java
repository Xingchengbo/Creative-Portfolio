public class Card {

	private Action cardAction;
	private String cardName;
	private String cardText;
	
	/** Create a card with the given name, body text, and associated Action*/
	public Card(Action action, String name, String text)
	{
		cardAction = action;
		cardName = name;
		cardText = text;
	}
	
	/** Returns the card's Action (probably to run Action.doAction(currPlayer) on)*/
	public Action getAction()
	{
		return cardAction;
	}
	
	/** Gets the name of the card (for formatting purposes)*/
	public String getName()
	{
		return cardName;
	}
	
	/** Gets the body text of the card (for formatting purposes)*/
	public String getText()
	{
		return cardText;
	}
	
}
