package mvc.model.player;


import mvc.model.pawn.Pawn;

import javax.swing.*;

public class Player {
    private String name;
    private PlayerColor color;
    private int ID;

    private JLabel[] pawns;

    /**
     * 
     * @param name
     * @param color
     * @param ID
     */
    public Player(String name, PlayerColor color, int ID){
        this.name=name;
        this.color=color;
        this.ID=ID;
    }


    public String getName() {
        return name;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int getID() {return ID;}


}
