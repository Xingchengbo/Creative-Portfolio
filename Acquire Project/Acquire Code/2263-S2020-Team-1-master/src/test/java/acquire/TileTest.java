package acquire;

import org.junit.*;
import static org.junit.Assert.*;
import javafx.scene.paint.Color;

public class TileTest {

    @Test
    public void getTileTest(){
        TileFactory tf = TileFactory.getTileFactory();
        Tile actual = tf.getTile("a1");
        assertTrue(actual.getLocation().equals("a1"));
    }

    @Test
    public void tileLocationTest(){
        TileFactory tf = TileFactory.getTileFactory();
        Tile actual = tf.getTile("a1");
        assertEquals("Does not return the location correctly", "a1", actual.getLocation());
    }

    @Test
    public void tileCreationTest(){
        TileFactory tf = TileFactory.getTileFactory();
        Tile expected = tf.getTile("a3");
        Tile other = tf.getTile("a3");
        assertEquals("The tiles are not the same object", expected, other);
    }

    @Test
    public void getColorTest(){
        TileFactory tf = TileFactory.getTileFactory();
        Tile actual = tf.getTile("a5");
        Color c = Color.WHITE;
        assertEquals(c, actual.getColor());
    }

    @Test
    public void getChainTest(){
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        Chain[] chains = board.getActiveChains();

        TileFactory tf = TileFactory.getTileFactory();
        Tile actual = tf.getTile("a1");
        actual.play();
        board.getActiveChains()[0].addTile(actual);
        Chain c1 = actual.getChain();
        assertEquals(chains[0], c1);
    }

    @Test
    public void dealableTest(){
        TileFactory tf = TileFactory.getTileFactory();
        Tile actual = tf.getTile("a1");
        actual.play();
        assertFalse(actual.dealable());
    }

    @Test
    public void playTest(){
        Board board = new Board();
        TileFactory tf = TileFactory.getTileFactory();
        Tile actual = tf.getTile("a1");
        actual.play();
        assertFalse(board.canPlayTile(actual));
    }


    @Test
    public void tileMergeTest(){
        fail();
    }
}
