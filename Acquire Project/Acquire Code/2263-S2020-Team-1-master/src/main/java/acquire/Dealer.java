package acquire;

import java.util.LinkedList;
import java.util.Random;

public class Dealer {
    private LinkedList<Tile> pile = new LinkedList<Tile>();
    private PlayerList<Player> playerOrder = new PlayerList();
    private TileFactory factory = TileFactory.getTileFactory();
    private Random rng = new Random();
    private static Dealer instance;

    //turns dealer into a singleton
    public static Dealer getDealerInstance(){
        if(instance == null){
            instance = new Dealer();
        }
        return instance;
    }

    private Dealer(){
        pile = new LinkedList<Tile>();
        //creates the pile
        for (char c = 'A'; c < 'J'; c++){
            for (int r = 1; r < 13; r++){
                pile.add(factory.getTile( (c +Integer.toString(r))));
                //System.out.println(c + Integer.toString(r) + "was created");

            }
        }
    }

    //Dealer checks and assigns an eligible tile to a player after they place a tile on the board. 
    public Tile dealTile(){
        if(!pile.isEmpty()){
            Tile toReturn = pile.get(rng.nextInt(pile.size()));
            removeFromPile(toReturn);
            return toReturn;
        }else{
            return null;
        }

    }
    
    //Dealer removes the tile, which has been assigned to a player, from the pile in the bank.
    private void removeFromPile(Tile t){
        pile.remove(t);
    }

    //After a player plays their turn, they end the turn and the dealer moves the turn over to the
    //next player.
    public void moveTurn(){

    }


    public void addPlayer(Player p){
        this.playerOrder.addLast(p);
    }


    public Player getPlayer(){
        return this.playerOrder.getCurrent();
    }

    public PlayerList getPlayerList(){
        return this.playerOrder;
    }

    public void reset(){
        Dealer.instance = new Dealer();
    }


}
