package mvc.model.turn;

/**
 * This class decides the turn of the game
 */
public class Turn {

    int currentPlayerID;

    /**
     * <b>Accessor:</b>method that indicates whom player's turn is
     * @return which player plays
     */
    public int getCurrentPlayerID(){
        return currentPlayerID;
    };

    /**
     * <b>Transformer:</b>sets the turn
     * <b>Post-Condition:</b> sets the right turn for the player to play
     */
    public void switchTurn(){
        if(currentPlayerID==1){
            currentPlayerID=2;
        }else {
            currentPlayerID=1;
        }
    }

    public Turn(int initialPlayerID){
        this.currentPlayerID=initialPlayerID;
    }

}
