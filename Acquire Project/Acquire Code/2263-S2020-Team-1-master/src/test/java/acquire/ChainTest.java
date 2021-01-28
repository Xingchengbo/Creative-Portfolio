package acquire;

import javafx.scene.paint.Color;
import org.junit.*;
import static org.junit.Assert.*;

public class ChainTest {

    @Test
    public void chainSizeTest(){
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        Chain[] chains = board.getActiveChains();
        chains[0].addTile(new Tile("a1"));
        chains[0].addTile(new Tile("a2"));
        chains[0].addTile(new Tile("a3"));
        int actual = chains[0].chainSize();
        assertEquals(3, actual);
    }

    @Test
    public void chainSizeTest_2(){
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        Chain[] chains = board.getActiveChains();
        int actual = chains[0].chainSize();
        assertEquals(0, actual);
    }


    @Test
    public void buyStocksTest(){
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        Chain[] chains = board.getActiveChains();
        chains[0].addTile(new Tile("a1"));
        chains[0].buyStocks(3);
        assertEquals(3, chains[0].getStocks());
    }

    @Test
    public void buyStocksTest_2(){
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        Chain[] chains = board.getActiveChains();
        chains[0].addTile(new Tile("a1"));
        chains[0].buyStocks(26);
        assertEquals(25, chains[0].getStocks());
        chains[0].buyStocks(3);
        assertEquals(25, chains[0].getStocks());
    }


    @Test
    public void chainAddTileTest(){
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        Chain[] chains = board.getActiveChains();
        chains[0].addTile(new Tile("a1"));
        assertEquals("a1", chains[0].getTilesInChain().get(0).getLocation());

        chains[0].addTile(new Tile("a2"));
        assertEquals("a2", chains[0].getTilesInChain().get(1).getLocation());
    }

    @Test(expected = NullPointerException.class)
    public void chainAddTileTest_2(){
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        Chain[] chains = board.getActiveChains();
        chains[0].addTile(new Tile(null));

    }

    @Test
    public void chainMergeTest(){
        fail();
    }
}
