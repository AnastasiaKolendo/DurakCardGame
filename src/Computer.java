import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

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
    public Card tossCard (List<Card> cardList) {
        List<Card> tossCards = new ArrayList<>();
        for (Card cardHand : hand) {
            for (Card card : cardList) {
                if (cardHand.getRank().getValue() == card.getRank().getValue()) {
                    tossCards.add(cardHand);
                }
            }
        }
        if (tossCards.size() == 0){
            System.out.println("Computer decided not to add cards ");
            return null;
        } else {
            Random random = new Random();
            int index = random.nextInt(tossCards.size());
            if (index == tossCards.size()){
                System.out.println("Computer decided to not add cards ");
                return null;
            } else {
                Card card = tossCards.get(index );
                return card;
            }
        }
    }
}
