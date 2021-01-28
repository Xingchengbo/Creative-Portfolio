public class HousePurchaseAction extends PurchaseAction {

	public HousePurchaseAction(Space s) {
		super(s);
	}

	/*
	@Override
	public void setSpaceAction(Player curPlayer, Space s) {
		HouseSpace space = (HouseSpace) s;
		for (int i = 0; i < 6; i++) {
			space.setAction(new PlayerPaymentAction(curPlayer, space.getRent(i)), i);
		}
	}
	*/
}
