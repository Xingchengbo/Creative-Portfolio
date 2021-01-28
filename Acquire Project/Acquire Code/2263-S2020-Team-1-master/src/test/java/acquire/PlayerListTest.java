package acquire;

import org.junit.*;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class PlayerListTest {


    @Test
    public void isEmptyTest(){
        PlayerList<Player> p = new PlayerList();
        assertTrue(p.isEmpty());
    }

    @Test
    public void sizeTest(){
        PlayerList<Player> p = new PlayerList();
        assertEquals(0, p.size());
        p.addLast(new Player("p1"));
        assertEquals(1, p.size());
    }
    @Test
    public void addLastTest(){
        PlayerList<Player> p = new PlayerList();
       p.addLast(new Player("p1"));
       p.addLast(new Player("p2"));
       assertEquals(2, p.size());
       p.getNext();
       Player t = (Player)  p.getNext();
       assertTrue(t.getID().equals("p2"));

    }

    @Test
    public void PlayerListFirstTest(){
        PlayerList<Player> p = new PlayerList();
        p.addLast(new Player("p1"));
        p.addLast(new Player("p2"));
        p.addLast(new Player("p3"));
        Player t = (Player)  p.first();
        assertEquals("p1", t.getID());
    }

    @Test
    public void getListTest(){
        PlayerList<Player> p = new PlayerList();
        p.addLast(new Player("p1"));
        p.addLast(new Player("p2"));
        p.addLast(new Player("p3"));
        LinkedList<Player> l1 = p.getList();
        LinkedList l2 = new LinkedList<>();
        l2.add("p1"); l2.add("p2"); l2.add("p3");
        assertTrue(l1.get(0).getID().equals(l2.get(0)));
        assertTrue(l1.get(1).getID().equals(l2.get(1)));
        assertTrue(l1.get(2).getID().equals(l2.get(2)));


    }

    @Test
    public void getCurrentTest(){
        PlayerList<Player> p = new PlayerList();
        p.addLast(new Player("p1"));
        p.addLast(new Player("p2"));
        p.addLast(new Player("p3"));
        assertTrue(p.getCurrent().getID().equals("p1"));
    }

    @Test
    public void getNextTest(){
        PlayerList<Player> p = new PlayerList();
        p.addLast(new Player("p1"));
        p.addLast(new Player("p2"));
        p.addLast(new Player("p3"));
        Player t = (Player)p.getNext();
        assertTrue(t.getID().equals("p1"));
        t = (Player)p.getNext();
        assertTrue(t.getID().equals("p2"));
    }
}
