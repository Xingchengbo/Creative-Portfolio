package acquire;

import javafx.scene.paint.Color;

public class Tile {
    //Location X and Y initialized for the location on the Board
    private TileFactory tf = TileFactory.getTileFactory();
    private String locationX;
    private String locationY;
    private boolean canDeal = true;
    private boolean isPlayed = false;
    private TileType tiletype;
    private Chain chain = null;

    //Constructor
    public Tile(String x){
        this.locationX = String.valueOf(x.charAt(0));
        this.locationY = String.valueOf(x.subSequence(1, x.length()));
        //10 is the black chain value
        this.tiletype = tf.getTileType(10);
    }

    public String getLocation(){
        return this.locationX + this.locationY;
    }

    public Tile getTile(String s){
        return tf.getTile(s);
    }

    public Color getColor(){
        if(this.isPlayed) {
            return tiletype.getColor();
        }
        return Color.WHITE;
    }

    public void setType(int ch){
        this.tiletype = tf.getTileType(ch);
    }

    public boolean dealable(){
        return this.canDeal;
    }

    public void play(){
        this.canDeal = false;
        this.isPlayed = true;
    }

    public Chain getChain(){
        return this.chain;
    }

    public boolean isPlayed() {
        return this.isPlayed;
    }

    public void changeChain(int chain, Chain c){
        this.tiletype = tf.getTileType(chain);
        this.chain = c;
    }

}
