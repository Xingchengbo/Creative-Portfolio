package acquire;

import javafx.scene.paint.Color;
import org.junit.*;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void playerIdTest1(){
        Player p = new Player("p");
        assertEquals("Didn't create single character name correctly", "p", p.getID());
    }

    @Test
    public void playerIdTest2(){
        Player p = new Player("p l");
        assertEquals("Didn't handle spaces correctly", "p l", p.getID());
    }

    @Test
    public void playerIdTest3(){
        Player p = new Player("1bh+-=/as{]'\"^%;");
        assertEquals("Didn't handle random Characters correctly", "1bh+-=/as{]'\"^%;", p.getID());
    }

    @Test
    public void negativeNetWorthTest(){
        Player p = new Player("p");
        int subtraction = p.getWorth();
        p.subtractMoney(subtraction + 100);
        assertEquals("Was able to get a negative worth", 0, p.getWorth());
    }

    @Test
    public void getHandMaxSizeTest(){
        Player p = new Player("Player1");
        LinkedList<Tile> l = p.getHand();
        assertTrue(l.size()<=6);
    }

    @Test
    public void playTileTest(){
        Player p = new Player("Player1");
        LinkedList<Tile> l = p.getHand();
        Tile t = l.get(0);
        p.playTile(t);
        l = p.getHand();
        assertNotEquals(t, l.get(0));
    }

    @Test
    public void getStocksOwnedTest_1(){
        Player p = new Player("Player1");
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  1);
        Chain[] chains = board.getActiveChains();
        assertEquals(0, p.getOwnedStocks(chains[0]));
    }

    @Test
    public void getStocksOwnedTest_2(){
        Player p = new Player("Player1");
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        Chain[] chains = board.getActiveChains();
        p.buyShare(chains[0], 2);
        assertEquals(2, p.getOwnedStocks(chains[0]));

        p.buyShare(chains[0], 2);
        assertEquals(4, p.getOwnedStocks(chains[0]));
    }

    @Test(expected = NullPointerException.class)
    public void getStocksOwnedTest_3(){
        Player p = new Player("Player1");
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        Chain[] chains = board.getActiveChains();
        System.out.println(Arrays.toString(chains));
        p.buyShare(chains[0], null);
    }


    @Test
    public void maxTiles(){
        Dealer d = Dealer.getDealerInstance();
        fail();
    }
}
