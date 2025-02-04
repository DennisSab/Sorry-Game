package mvc.model.card;

public interface Card {


    /**
     * Retrieves the numerical value of the current card.
     *
     * @return The numerical value of the card.
     */
    public int getValue();

    /**
     * Sets the numerical value of the current card.
     *
     * @param value The value to set for the card.
     */
    public void setValue(int value);

    /**
     * Retrieves the image name associated with the card.
     *
     * @return The image name of the card.
     */
    public String getImage();

    /**
     * Sets the image name for the card.
     *
     * <p>
     * Note: The image name should represent the visual representation of the card.
     * </p>
     *
     * @param image The name of the image to set for the card.
     */
    public void setImage(String image);


}
