import javax.swing.JOptionPane;

public class RailroadPurchaseAction implements Action {

	Space space;
	
	public RailroadPurchaseAction(Space s) {
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
			space.setAction(new RailroadAction(currentPlayer));
		}
	}
}
