import javax.swing.JOptionPane;

public class PlayerPaymentAction implements Action {
	
	private Player _owner;
	private int _amount;

	public PlayerPaymentAction(Player owner, int amount) {
		_owner = owner;
		_amount = amount;
	}
	
	/**
	 * Takes away an amount of money from curPlayer and gives it
	 * to owner player.
	 */
	public void doAction(Player curPlayer) {
		
		JOptionPane.showMessageDialog(null, "You must pay Player " + (_owner.getID()+1) + " " + _amount + "Åí.");
		
		curPlayer.changeMoney(-_amount);
		//need decision on how bankruptcy is checked. if here than need addition here.

		_owner.changeMoney(_amount);
	}
}
