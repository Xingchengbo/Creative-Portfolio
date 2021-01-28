package acquire;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class Board {
    private Tile[][] tiles = new Tile[9][12];
    private Chain[] activeChains = new Chain[7];
    private TileFactory factory = TileFactory.getTileFactory();

    public Board(){
        //creates 108 tiles
        for (char c = 'A'; c < 'J'; c++){
            for (int r = 1; r < 13; r++){
                //stores the tiles in a 2d array
                tiles[c-65][r-1] = factory.getTile( (c +Integer.toString(r)));
                //System.out.println(c + Integer.toString(r) + "was created");

            }
        }
    }

    public Tile getTile(String s){
        int x = (s.charAt(0) - 65);
        int y = Integer.parseInt(s.substring(1)) -1 ;
        if ((x >= 0 && y >= 0) && (x<9 && y< 12)) {
            return this.tiles[x][y];
        }else{
            return null;
        }
    }


    public boolean canPlayTile(Tile toPlay){
        return false;
    }

    public void placeTile(Tile toPlay){
        // culmination of methods Janita made, then send it to the ui frontend.
    }

    //placeTile will call this if the placed tile would cause a merge
    public boolean mergeChains(Tile t1, Tile t2) {

        if ((t1.getChain() != null && t2.getChain() != null)&&(t1.getChain() != t2.getChain())) {
            int chain1size = t1.getChain().chainSize();
            int chain2size = t2.getChain().chainSize();
            if(chain1size > chain2size){
                t2.getChain().merge(t1.getChain());
            }else{
                t1.getChain().merge(t2.getChain());
            }
        }else if (t1.getChain() == null && t2.getChain() == null){
            return true;
        }else if (t1.getChain() == null){
            t2.getChain().addTile(t1);
        }else if (t2.getChain() == null){
            t1.getChain().addTile(t2);
        }
        return false;
    }

    public void addChain(String n, Color c, int chainNum){
            this.activeChains[chainNum] = new Chain(n, c, chainNum);
    }

    public Chain[] getActiveChains(){
        return this.activeChains;
    }

    public boolean canCreateNewStock(){
        boolean can = false;
        for (Chain activeChain : activeChains) {
            if(activeChain.chainSize() == 0){
                can = true;
            }
        }
        return can;
    }

    public boolean canEnd(){
        //checks if end conditions are met
        return false;
    }
}
