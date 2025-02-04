package mvc.model.Deck;

import mvc.model.card.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to represent a deck of cards
 */
public class Deck  {

    ArrayList<Card> cards=new ArrayList<Card>();


    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }


    /**
     * <b>Transformer:</b>Initializes and shuffles the 44 cards
     * <b>Postcondition:</b>The cards have been initialized and shuffled
     */
    public void init_cards(){

       for(int j=0;j<4;j++) {
           for (int i = 1; i <= 12; i++) {
               if (i == 1) {
                   Card card=new NumberOneCard();
                   cards.add(card);
               } else if (i == 2) {
                   Card card=new NumberTwoCard();
                   cards.add(card);
               } else if (i == 3) {
                   Card card=new SimpleNumberCard(3);
                   cards.add(card);
               } else if (i == 4) {
                   Card card=new NumberFourCard();
                   cards.add(card);
               } else if (i == 5) {
                   Card card=new SimpleNumberCard(5);
                   cards.add(card);
               } else if (i == 6) {
                   Card card=new SorryCard();
                   cards.add(card);
               } else if (i == 7) {
                   Card card=new NumberSevenCard();
                   cards.add(card);
               } else if (i == 8) {
                   Card card=new SimpleNumberCard(8);
                   cards.add(card);
               } else if (i == 10) {
                   Card card=new NumberTenCard();
                   cards.add(card);
               } else if (i == 11) {
                   Card card=new NumberElevenCard();
                   cards.add(card);
               } else if (i == 12) {
                   Card card=new SimpleNumberCard(12);
                   cards.add(card);
               }
           }
       }

        Collections.shuffle(cards);
    };


}
