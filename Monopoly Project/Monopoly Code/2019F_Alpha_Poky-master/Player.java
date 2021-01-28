import java.util.ArrayList;

public class Player {

	private int curSpace;
	private int money;
	private int turnsInJail;
	private ArrayList<Space> ownedSpaces;
	private boolean goojf;
	private int ID;
	
	public Player(int ID)
	{
		this.curSpace = 0; //0 is the 'Go' space
		this.money = 1500; //$1500 is the starting cash
		this.ownedSpaces = new ArrayList<Space>();
		this.goojf = false; //Get Out of Jail Free
		this.turnsInJail = 0;
		this.ID = ID;
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	public void changeMoney(int change)
	{
		this.money += change;
	}
	
	public int getSpace()
	{
		return curSpace;
	}
	
	public void setSpace(int curSpace)
	{
		this.curSpace = curSpace;
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public int getJail()
	{
		return turnsInJail;
	}
	
	public void setJail(int jailTime)
	{
		this.turnsInJail = jailTime;
	}
	
	public boolean getGooj()
	{
		return this.goojf;
	}
	
	public void setGooj(boolean gooj)
	{
		this.goojf = gooj;
	}
	
	public void addSpace(Space space)
	{
		this.ownedSpaces.add(space);
	}

	public void removeSpace(Space space)
	{
		this.ownedSpaces.remove(ownedSpaces.indexOf(space));
	}
	
	public ArrayList<Space> getOwnedSpaces()
	{
		return ownedSpaces;
	}
}
