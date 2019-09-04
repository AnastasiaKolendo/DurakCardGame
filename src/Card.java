

enum Suit {
    DIAMONDS ("Diamonds"), SPADES ("Spades"), HEARTS ("Hearts"), CLUBS ("Clubs");
    private final String name;
    Suit (String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}

enum Rank {
    ACE(14, "Ace"), KING(13, "King"), QUEEN(12, "Queen"), JACK(11, "Jack"),
    TEN(10, "10"), NINE(9, "9"), EIGHT(8, "8"), SEVEN(7, "7"), SIX(6, "6");
    private int value;
    private final String name;
    Rank (String name){
        this.name = name;
    }

    Rank(int value, String name) {
        this.value = value;
        this.name = name;
    }


    public int getValue() {
        return value;
    }

    public String getName(){
        return name;
    }
}

public class Card {

    private final Suit suit;
    private final Rank rank;


    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank.getName() + " " + suit.getName();
    }

    public boolean beats(Card another, Suit trump){
        if(suit == another.suit){
            return rank.getValue() > another.rank.getValue();
        } else if(suit == trump){
            return true;
        } else if(another.suit == trump){
            return false;
        } else{
            return false;
        }
    }
}


