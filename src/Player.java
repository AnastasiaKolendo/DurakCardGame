
import java.util.Comparator;
import java.util.List;

public abstract class Player {
    protected final List<Card> hand;
    private final String name;

    public Player(String name, List<Card> hand) {
        this.name = name;
        this.hand = hand;
    }

    public void sortCards(Suit trump){
        Comparator<Card> comparator = (c1, c2) -> {
            if (c1.getSuit() == trump && c2.getSuit() != trump) {
                return 1;
            } else if (c1.getSuit() != trump && c2.getSuit() == trump) {
                return -1;
            } else {
                return Integer.compare(c1.getRank().getValue(), c2.getRank().getValue());
            }
        };
        hand.sort(comparator);
    }

    public Card getSmallestOfSuit(Suit suit) {
        Card smallest = null;
        for (Card card : hand) {
            if (card.getSuit() == suit) {
                if (smallest == null || card.getRank().getValue() < smallest.getRank().getValue()) {
                    smallest = card;
                }
            }
        }
        return smallest;
    }

    public String getName() {
        return name;
    }

    public boolean isHandEmpty() {
        return hand.isEmpty();
    }

    public abstract Card attack(Suit trump);

    public abstract Card defend(Card againstCad, Suit trump);

    public abstract Card tossCard(List<Card> cardList);

}
