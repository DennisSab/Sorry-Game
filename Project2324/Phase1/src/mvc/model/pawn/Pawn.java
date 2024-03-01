package mvc.model.pawn;

import mvc.model.player.Player;

import java.awt.*;

public class Pawn {

    Color color;
    String name;


    public Pawn(String name, Player player, Color color){
        this.name=name;
        this.color=color;
    };


}
