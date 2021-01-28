package acquire;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class TileType{

    //get the color of the chain it is contained in.
    private Color c;
    Rectangle border = new Rectangle(50, 50);

    public TileType(int ch){
        if(ch > 6){
            this.c = Color.BLACK;
        }else if(ch == 0){
            this.c = Color.YELLOW;
        }else if(ch == 1){
            this.c = Color.PINK;
        }else if(ch == 2){
            this.c = Color.LIGHTBLUE;
        }else if(ch == 3){
            this.c = Color.BROWN;
        }else if(ch == 4){
            this.c = Color.RED;
        }else if(ch == 5){
            this.c = Color.GREEN;
        }else if(ch == 6){
            this.c = Color.BLUE;
        }
    }

    public Color getColor(){
        return this.c;
    }
}
