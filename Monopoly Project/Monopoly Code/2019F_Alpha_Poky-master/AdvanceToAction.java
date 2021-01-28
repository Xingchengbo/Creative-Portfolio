

public class AdvanceToAction implements Action {

  private int advanceNum;

  public AdvanceToAction(int count) {
    advanceNum = count;
  }
  
  public void doAction(Player currentPlayer) {
    // Algorithm to move player to new location, with a check of whether they passed go.
    // If they passed go, run a BankPaymentAction to pay them 200.
	  
	  if(advanceNum == -1)
	  {
		  currentPlayer.setSpace(0);
		  Action a = new BankPaymentAction(200);
		  a.doAction(currentPlayer); //Pay $200
	  }
	  
	  else
	  {
		    // We must then run the space action, with proper ui prompts
		  
		  if(currentPlayer.getSpace() + advanceNum > 39)
		  {
			  Action a = new BankPaymentAction(200);
			  a.doAction(currentPlayer); //Pay $200
		  }
		  
		  currentPlayer.setSpace((currentPlayer.getSpace() + advanceNum) % 40);
	  }
	  

  }

}
