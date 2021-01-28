import javax.swing.JOptionPane;

public class PurchaseAction implements Action {

	private Space space;

	protected PurchaseAction(Space s) {
		space = s;
	}
	  
	public void doAction(Player currentPlayer)
	{
		int choice = JOptionPane.showConfirmDialog(null, "Would you like to purchase for " + space.getPrice() + "Åí?");

		if (choice == JOptionPane.YES_OPTION) {
			currentPlayer.addSpace(space);

			// Charge Player
			currentPlayer.changeMoney(-space.getPrice());

			// Make needed changes to space object to be player owned
			// Function implemented in subclasses.
			space.setAction(new PlayerPaymentAction(currentPlayer,space.getRent()));
		}
	}
	
	//public abstract void setSpaceAction(Player curPlayer, Space s);
}
