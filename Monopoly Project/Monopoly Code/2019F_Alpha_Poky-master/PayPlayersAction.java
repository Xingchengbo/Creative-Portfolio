/**
 * Action to have all player either be paid by or
 * pay a single player.
 * A positive input amount means curPlayer will be paid
 * that amount by each player.
 * A negative amount means curPlayer will pay that
 * amount to each player.
 */
public class PayPlayersAction implements Action {

	private int amount;
	private Player[] players;

	public PayPlayersAction(int amount, Player[] players) {
		this.amount = amount;
		this.players = players;
	}

	public void doAction(Player curPlayer) {
		for (Player p : players) {
			if (p != curPlayer) {
				curPlayer.changeMoney(amount);
				p.changeMoney(-amount);
			}
		}
	}

}
