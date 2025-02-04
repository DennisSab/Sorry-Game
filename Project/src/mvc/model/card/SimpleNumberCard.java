package mvc.model.card;

/**
 * this class is about 3,5,8,12 cards
 */

public class SimpleNumberCard extends NumberCard{


    /**
     * Constructor
     * @param value
     */
    public SimpleNumberCard(int value){
        super(value,"mvc/images/cards/card"+value+".png");
    }

}
