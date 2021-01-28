package acquire;

import javafx.scene.paint.Color;
import org.junit.*;
import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void boardCanPlayTest(){
        //requires chains to be implemented.
        fail();
    }

    @Test
    public void boardCreationTest(){
        Board b = new Board();
        TileFactory tf = TileFactory.getTileFactory();

        for (char c = 'A'; c < 'J'; c++){
            for (int r = 1; r <= 11; r++){
                assertEquals((c +Integer.toString(r)), b.getTile(c + Integer.toString(r)).getLocation());
            }
        }

    }

    @Test
    public void boardChainCreationTest(){
        Board board = new Board();
        board.addChain("T", Color.YELLOW,  0);
        board.addChain("I", Color.PINK,  1);
        board.addChain("C", Color.LIGHTBLUE, 2);
        board.addChain("W", Color.BROWN, 3);
        board.addChain("L", Color.RED, 4);
        board.addChain("F", Color.GREEN,  5);
        board.addChain("A", Color.BLUE,   6);

        assertEquals(7, board.getActiveChains().length);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void boardMaxChainTest_2(){
        //create 7 chains
        //attempt to create another, but will not get created
        Board board = new Board();
        board.addChain("mergeChain", Color.BLACK,  0);
        board.addChain("T", Color.YELLOW,  1);
        board.addChain("I", Color.PINK,  2);
        board.addChain("C", Color.LIGHTBLUE, 3);
        board.addChain("W", Color.BROWN, 4);
        board.addChain("L", Color.RED, 5);
        board.addChain("F", Color.GREEN,  6);
        board.addChain("A", Color.BLUE,   7);

        board.addChain("E", Color.ORANGE,   8);

        assertEquals(7, board.getActiveChains().length);
    }
}
