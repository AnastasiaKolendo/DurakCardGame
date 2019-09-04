import java.util.Random;
import java.util.Scanner;

public class DurakCardGame {
    public static void main(String[] args) {

        System.out.println("What is your name?");
        String playerName = new Scanner(System.in).nextLine();
        System.out.println();

        Deck deck = Deck.createShuffled();
        Player player1 = new User(playerName, deck.dealCard(6));
        Player player2 = new Computer("Computer", deck.dealCard(6));

        Card bottom = deck.getBottom();
        Suit trump = bottom.getSuit();
        System.out.println("Trump is " + trump.getName());

        Player attacker = findAttacker(player1, player2, trump);
        Player defender;
        if (attacker == player1) {
            defender = player2;
        } else {
            defender = player1;
        }

        while (true){
            System.out.println();

            addCards(deck, attacker);
            addCards(deck, defender);
            System.out.println("The remaining number of cards in the deck is " + deck.returnSize());
            Card attack = attacker.attack(trump);
            System.out.println(attacker.getName() + " attacks with " + attack);
            Card defend = defender.defend(attack, trump);
            if (defend != null){
                System.out.println(defender.getName() + " defends with " + defend);
                Player tmp = attacker;
                attacker = defender;
                defender = tmp;
            } else {
                System.out.println(defender.getName() + " decided to get the card ");
            }

            if (attacker.isHandEmpty() && defender.isHandEmpty()){
                System.out.println("Tie!");
                return;
            } else if(attacker.isHandEmpty()){
                System.out.println(attacker.getName() + " wins!");
                return;
            } else if (defender.isHandEmpty()){
                System.out.println(defender.getName() + " wins!");
                return;
            }

        }
    }

    private static void addCards(Deck deck, Player player) {
        while (true) {
            if (player.hand.size() >= 6) return;
            Card card = deck.addCard();
            if (card == null) {
                return;
            }
            player.hand.add(card);
        }
    }

    public static Player findAttacker(Player player1, Player player2, Suit trump) {
        Card p1smallest = player1.getSmallestOfSuit(trump);
        Card p2smallest = player2.getSmallestOfSuit(trump);
        if (p1smallest == null && p2smallest == null) {
            System.out.println("No trumps! Fate!");
            if (new Random().nextBoolean()){
                System.out.println(player1.getName() + " is first.");
                return player1;
            } else {
                System.out.println(player2.getName() + " is first.");
                return player2;
            }
        } else if( p1smallest == null){
            System.out.println(player1.getName() + " doesn't have trumps.");
            System.out.println(player2.getName() + " is first.");
            return player2;
        } else if(p2smallest == null){
            System.out.println(player2.getName() + " doesn't have trumps.");
            System.out.println(player1.getName() + " is first.");
            return player1;
        } else {
            Rank rank1 = p1smallest.getRank();
            Rank rank2 = p2smallest.getRank();
            System.out.print(player1.getName() + " has " + p1smallest.toString() + ". ");
            System.out.println(player2.getName() + " has " + p2smallest.toString() + ".");
            if (rank1.getValue() > rank2.getValue()){
                System.out.println(player2.getName() + " is first.");
                return player2;
            } else {
                System.out.println(player1.getName() + " is first.");
                return player1;
            }
        }

    }
}
