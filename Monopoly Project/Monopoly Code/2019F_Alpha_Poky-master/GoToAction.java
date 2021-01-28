public class GoToAction implements Action {

	Space _space;
	
	  public GoToAction(Space space)
	  {
		  _space = space;
	  }
	  
	  public void doAction(Player currentPlayer)
	  {
	  
		  //Move the player around the  board; modulo to wrap around back to Go
		  currentPlayer.setSpace(_space.getID());
		  _space.getAction().doAction(currentPlayer);
		  
	  }
	
}
