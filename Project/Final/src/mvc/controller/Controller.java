package mvc.controller;


import mvc.model.player.PlayerColor;
import mvc.model.turn.Turn;
import mvc.view.View;
import mvc.model.card.Card;
import mvc.model.player.Player;
import mvc.model.Deck.Deck;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Controller is the master of the game and controls all
 * the operations executed
 */
public class Controller {

     View view;
     List <Card> allCards;

     Player p1;
     Player p2;

     Turn turn;

     JLabel [] pawns;

     JLabel [] pawnP1=new JLabel[2];

     JLabel [] pawnP2=new JLabel[2];




    /**
     * <b>Constructor:</b>initializes players and calls other functions in order to initialize the game aw well <br />
     * <b>Post-Condition:</b>sets the game ready to start
     */
    public void initialize() {


         turn=new Turn(1);
         view = new View();
         view.initComponents();
         view.setVisible(true);
         setCards();
         setListeners();
         setPawns();

         p1=new Player("Player1",PlayerColor.RED,1);
         p2=new Player("Player2",PlayerColor.YELLOW,2);

        view.updateInfobox("Info Box\n\nTurn:"+ p1.getName()+"("+p1.getColor()+")"+
                "\nCards Left:"+allCards.size());

    }

    /**
     * <b>transformer(mutative):</b> initializes cards
     * <p><b>Post-Condition:</b>initializes cards at the beginning</p>
     */
    public void setCards() {

        Deck d1=new Deck();
        d1.init_cards();
        this.allCards=d1.getCards();

    }


    /**
     * set Listeners for the buttons
     */
    public void setListeners(){
        view.getDrawButton().addMouseListener(new DrawListener());
        view.getFoldButton().addMouseListener(new FoldListener());
    }

    /**
     * set the pawns for the players
     */
    public void setPawns(){
        pawns=view.getPaws();
        pawnP1[0]=pawns[0];
        pawnP1[1]=pawns[1];
        pawnP2[0]=pawns[2];
        pawnP2[1]=pawns[3];
    }


    /**
     * method that controls the game
     * updates the game when needed or ends it
     */
    public void updateCurrent(){
        String path=allCards.get(0).getImage();
        int steps=allCards.get(0).getValue();

        if(turn.getCurrentPlayerID()==1){
            view.updatePawn(p1,steps);
            view.updateCurrentCard(path);
            view.updateInfobox("Info Box\n\nTurn:"+ p2.getName()+"("+p2.getColor()+")"+
                    "\nCards Left:"+allCards.size());
            turn.switchTurn();
            allCards.remove(0);
            if (allCards.isEmpty()){
                setCards();
            }

            if (steps==2){
                turn.switchTurn();
                view.updateInfobox("Info Box\n\nTurn:"+ p1.getName()+"("+p1.getColor()+")"+
                        "\nCards Left:"+allCards.size());
            }
            if(steps==8 && view.isDrawCard8()){
                turn.switchTurn();
                view.updateInfobox("Info Box\n\nTurn:"+ p1.getName()+"("+p1.getColor()+")"+
                        "\nCards Left:"+allCards.size());
            }
            if (steps==12 && view.isDrawCard12()){
                turn.switchTurn();
                view.updateInfobox("Info Box\n\nTurn:"+ p1.getName()+"("+p1.getColor()+")"+
                        "\nCards Left:"+allCards.size());
            }
            view.gameFIN();
        }else if(turn.getCurrentPlayerID()==2){
            view.updatePawn(p2,steps);
            view.updateCurrentCard(path);
            view.updateInfobox("Info Box\n\nTurn:"+ p1.getName()+"("+p1.getColor()+")"+
                    "\nCards Left:"+allCards.size());
            turn.switchTurn();
            allCards.remove(0);
            if (allCards.isEmpty()){
                setCards();
            }
            if(steps==8 && view.isDrawCard8()){
                turn.switchTurn();
                view.updateInfobox("Info Box\n\nTurn:"+ p2.getName()+"("+p2.getColor()+")"+
                        "\nCards Left:"+allCards.size());
            }

            if (steps==12 && view.isDrawCard12()){
                turn.switchTurn();
                view.updateInfobox("Info Box\n\nTurn:"+ p2.getName()+"("+p2.getColor()+")"+
                        "\nCards Left:"+allCards.size());
            }

            if (steps==2){
                turn.switchTurn();
                view.updateInfobox("Info Box\n\nTurn:"+ p2.getName()+"("+p2.getColor()+")"+
                        "\nCards Left:"+allCards.size());
            }
            view.gameFIN();
        }

    }


    /**
     * creates a new instance of Controller which sets the game ready to start
     * @param args
     */
    public static void main(String[] args) {
        Controller c=new Controller();
        c.initialize();
    }



    public class DrawListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)){
                updateCurrent();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }
    }


    public class FoldListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {


        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

    }



}
