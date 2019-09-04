import sun.awt.geom.AreaOp;

import java.util.Comparator;
import java.util.List;

public class Computer extends Player{
    public Computer(String name, List<Card> hand) {
        super(name, hand);
    }

    @Override
    public Card attack(Suit trump) {
        Comparator<Card> comparator = (c1, c2) -> {
            if(c1.getSuit() == trump && c2.getSuit() != trump){
                return 1;
            } else if (c1.getSuit() != trump && c2.getSuit() == trump){
                return -1;
            } else {
                return Integer.compare(c1.getRank().getValue(), c2.getRank().getValue());
            }
        };

        Card smallest = hand.get(0);
        for (Card card: hand){
            if (comparator.compare(smallest, card) > 0){
                smallest = card;
            }
        }
        hand.remove(smallest);
        return smallest;
    }

    @Override
    public Card defend(Card againstCart, Suit trump) {
        for (Card card: hand){
            if (card.beats(againstCart, trump)){
                hand.remove(card);
                return card;
            }
        }
        hand.add(againstCart);
        return null;
    }
}
