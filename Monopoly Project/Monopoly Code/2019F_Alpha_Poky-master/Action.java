/**
 * Interface for game actions. These are actions that 
 * various game components fire off, such as payments,
 * movement, etc.
 */
public interface Action {

public void doAction(Player curPlayer);

}
