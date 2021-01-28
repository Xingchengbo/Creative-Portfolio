import javax.swing.JOptionPane;

/**
 * Pays the current player the amount specified. 
 * If the amount is negative, the player pays the bank.
 */
public class BankPaymentAction implements Action {

  private int _payment;

  public BankPaymentAction(int payment) {
    _payment = payment;
  }

  @Override
  public void doAction(Player curPlayer) {
	  
	  if(_payment < 0)
	  {
		  JOptionPane.showMessageDialog(null, "You must pay " + -_payment + "’.");
	  }
	  
	  else
	  {
		  JOptionPane.showMessageDialog(null, "You receive " + _payment + "’.");
	  }
	  
    curPlayer.changeMoney(_payment);
  }


}
