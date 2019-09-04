import java.util.*;

public class Deck {
     private List<Card> cards;

     public Deck(List<Card> cards) { this.cards = cards;}

     public static Deck createShuffled(){
         List<Card> cards = new ArrayList<>();
         for (Suit s : Suit.values()) {
             for (Rank r : Rank.values()) {
                 cards.add(new Card(s, r));
             }
         }
         Collections.shuffle(cards);
         return new Deck(cards);
     }


     public List<Card> dealCard(int n){
         List<Card> dealCards = new ArrayList<>(n);
         for (int i = 0; i < n; i++) {
             dealCards.add(this.cards.remove(this.cards.size() - 1));
         }
         return dealCards;

     }
     public Card addCard(){
         if (cards.size() > 0) {
             Card card = cards.get(cards.size() - 1);
             cards.remove(cards.size() - 1);
             return card;
         }
         return null;
     }

     public Card getBottom(){
         return this.cards.get(0);
     }

     public int returnSize(){
         return this.cards.size();
     }
}