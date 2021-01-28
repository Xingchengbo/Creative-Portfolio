package acquire;

import java.util.Random;

public class MergeHandler {


    //is this class going to interact with the players directly
    //or is it going to pass the info to the board, which
    //in turn sends it to the players?

    //do we want the chain to hold which players have stock in them
    //to make this easier?

    //we will likely need to pass the players who own stock in the chain to here
    public void mergeChains(Chain[] chains){
        Random random = new Random();
        //find which chain is larger.
        if (chains[0].chainSize() > chains[1].chainSize()){
            chains[1].merge(chains[0]);
        }
        //call merge on the chain.

        //if the two chains are equal in size
        else if (chains[0].chainSize() == chains[1].chainSize()){
            int randomNumber = random.nextInt();

            if (randomNumber < 0.5){
                chains[1].merge(chains[0]);
            }
            else {
                chains[0].merge(chains[1]);
            }
        }
        else if (chains[0].chainSize() < chains[1].chainSize()){
            chains[0].merge(chains[1]);
        }
        else {
            System.out.println("Errors(mergeChains)");
        }

        //will call all the private methods to determine how to merge
    }

    //checks the Players count of stocks to search for ties
    private void determineTies(Player[] players){

    }

    //If there is no tie for the largest, Find the largest
    private Player findLargestShareholder(Player[] players){
        return null;
        //returns whoever has the highest share value
    }

    //If there is no tie for second, finds the Second largest
    private Player findSecondShareholder(Player[] players){
        return null;
        //returns whoever has the second highest value
    }

    //distributes bonus based on ties. We will probably need to pass
    //some value to it besides bonus to tell it which
    //kind of tie it is.
    private void distributeTieBonus(Player[] players){

    }

    //divides the bonus based on the above functions.
    private void giveBonuses(){
        // do we want this to return an int?
    }
}
