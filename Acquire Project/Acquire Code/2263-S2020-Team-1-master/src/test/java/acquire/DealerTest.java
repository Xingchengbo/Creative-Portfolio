package acquire;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class DealerTest {
    private Dealer d;

    @Before
    public void init(){
        this.d = Dealer.getDealerInstance();
    }

    @After
    public void close(){
        d.reset();
    }

    @Test
    public void dealAllTilesTest(){
        //deal 108 tiles
        //try to deal a 109th tile <-- dealAllTilesTest_2
        LinkedList<Tile> l = new LinkedList<>();
        for (int i = 0; i<108; i++){
            l.add(d.dealTile());
        }
        assertEquals(108, l.size());
    }

    @Test
    public void dealAllTilesTest_2(){
        LinkedList<Tile> l = new LinkedList<>();
        for (int i = 0; i<108; i++){
            l.add(d.dealTile());
        }
        l.add(d.dealTile());
        assertEquals(null, l.get(108));
    }

    @Test
    public void duplicateTileDealtTest(){
        //deal all 108 tiles, and make sure there are no duplicates
        //fail();
        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i<108; i++){
            l.add(d.dealTile().getLocation());
        }

        Set<String> temp = new HashSet();
        for(String value : l){
            if(!temp.contains(value)){
                temp.add(value);
            }
        }

        assertEquals(108, temp.size());
    }

    @Test
    public void dealTileTest() {

        LinkedList pile = new LinkedList<Tile>();
        //creates the pile
        for (char c = 'A'; c < 'J'; c++){
            for (int r = 1; r < 13; r++){
                //Tile t = new Tile(c +Integer.toString(r));
                pile.add(c +Integer.toString(r));
            }
        }
        Tile tile = d.dealTile();
        //System.out.println(tile.getLocation());
        for (char c = 'A'; c < 'J'; c++){
            for (int r = 1; r < 13; r++){
                if (tile.getLocation().equals(c +Integer.toString(r)))
                    pile.remove(c +Integer.toString(r));
            }
        }
        assertEquals(107, pile.size());
    }


    @Test
    public void moveTurnTest() {
        Player[] players = new Player[3];
        for (int i = 0; i < 3; i++) {
            players[i] = new Player(Integer.toString(i));
            d.addPlayer(players[i]);
        }
        for (int i = 2; i == 0; i--) {
            assertEquals(players[i], d.getPlayer());
            d.moveTurn();
        }
    }

    @Test
    public void addPlayerTest() {
        assertEquals(0, d.getPlayerList().size());

        d.addPlayer(new Player("p1"));
        d.addPlayer(new Player("p2"));
        assertEquals(2, d.getPlayerList().size());

    }

    @Test
    public void getPlayerTest() {
        assertEquals(0, d.getPlayerList().size());

        d.addPlayer(new Player("p1"));
        d.addPlayer(new Player("p2"));
        assertEquals("p1", d.getPlayer().getID());

    }
    
}
