import org.omg.CORBA.Object;

import java.util.ArrayList;
import java.util.List;

public abstract class Player{
    protected final List<Card> hand;
    private final String name;
    public Player(String name, List<Card> hand){
        this.name = name;
        this.hand = hand;
    }


    public Card getSmallestOfSuit(Suit suit) {
        Card smallest = null;
        for (Card card : hand) {
            if(card.getSuit() == suit) {
                if (smallest == null || card.getRank().getValue() < smallest.getRank().getValue()) {
                    smallest = card;
                }
            }
        }
        return smallest;
    }

    public String getName(){
        return name;
    }

    public boolean isHandEmpty(){ return hand.isEmpty();}
    public abstract Card attack(Suit trump);
    public abstract Card defend(Card againstCad, Suit trump);

}
