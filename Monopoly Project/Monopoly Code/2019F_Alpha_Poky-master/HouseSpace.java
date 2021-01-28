public class HouseSpace extends Space {

	// Holds rent values for various house counts
	// Hotel is rent is stored at index 5
	private int[] rent = new int[6];
	private Action[] action = new Action[6];

	private int _houseCount = 0;
	
	/**
	 * Constructs a space that can have houses built. Uses rent for no houses
	 * to guess the rent with x houses. It should be noted this is often incorrect.
	 * In particular, 4 houses has no real pattern for the price, and such will
	 * always be wrong. If different rent values are wanted, setRent should be called 
	 * after construction.
	 */
	public HouseSpace(int x, int y, int price, int rent) {
		super(x,y, price);
		this.rent[0] = rent;
		// Typical values of rent for a space, not always consistent.
		// If more correct values are needed, setRent should be used to specify.
		this.rent[1] = 5 * rent;
		this.rent[2] = 3 * this.rent[1];
		this.rent[3] = (int) Math.round(((6 * this.rent[1]) + 140) / 50.0) * 50; // rounding result to nearest 50
		this.rent[4] = (7 * this.rent[1]) + 210;
		this.rent[5] = (6 * this.rent[1]) + 600;
	}

	public int getRent(int houseCount) {
		return rent[houseCount];
	}

	public void setRent(int houseCount, int rent) {
		this.rent[houseCount] = rent;
	}

	@Override
	public void setAction(Action a) {
		action[0] = a;
	}

	/**
	 * Sets the action for a specific house count.
	 * Action will be returned if getAction is called with houseCount houses
	 * on the space.
	 */
	public void setAction(Action a, int houseCount) {
		action[houseCount] = a;
	}

	@Override
	public Action getAction() {
		return action[_houseCount];
	}
}
