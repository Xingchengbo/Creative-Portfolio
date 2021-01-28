import javax.swing.JOptionPane;

public class RailroadAction implements Action {

	private Player owner;

	public RailroadAction(Player owner) {
		this.owner = owner;
	}

	public void doAction(Player curPlayer) {
		// get player railroad count
		int ownerRR = 0;
		for (Space s : owner.getOwnedSpaces()) {
			if (s.getAction() instanceof RailroadAction) {
				ownerRR++;
			}
		}
		
		JOptionPane.showMessageDialog(null, "You must pay Player " + (owner.getID()+1) + " " + (ownerRR * 50) + "Åí.");
		
		// charge that amount
		curPlayer.changeMoney(-ownerRR * 50);
		owner.changeMoney(ownerRR * 50);
	}

}
