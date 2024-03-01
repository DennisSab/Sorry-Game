package mvc.model.card;

public class NumberCard implements Card{

    private int value;

    private String image;



    /**
     * Constructor to create a Number Card with the specified numerical value.
     *
     * @param value The numerical value of the Number Card.
     */
    NumberCard(int value,String image){
        this.value=value;
        this.image=image;
    }

    /**
     * Retrieves the numerical value of the Number Card.
     *
     * @return The numerical value of the Number Card.
     */
    @Override
    public int getValue() {
        return value;
    }


    /**
     * Sets the numerical value of the Number Card.
     *
     * @param value The value to set for the Number Card.
     */
    @Override
    public void setValue(int value) {
        this.value=value;
    }

    /**
     * Retrieves the image name associated with the Number Card.
     *
     * @return The image name of the Number Card.
     */
    @Override
    public String getImage() {
        return image;
    }

    /**
     * Sets the image for the Number Card (not implemented for this class).
     *
     * @param image The image to set for the Number Card.
     */
    @Override
    public void setImage(String image) {
        this.image=image;
    }


}
