import java.util.ArrayList;
import java.util.List;
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
            int startSize = defender.hand.size();

            System.out.println(attacker.getName() + " has " + attacker.hand.size() + " cards. " +
                     defender.getName() + " has " + defender.hand.size() + " cards");
            System.out.println();

            attacker.sortCards(trump);
            defender.sortCards(trump);
            if (deck.returnSize() > 0) {
                System.out.println("The remaining number of cards in the deck is " + deck.returnSize());
                System.out.println();
            } else {
                System.out.println("There are no more cards in the deck.");
                System.out.println();
            }

            Card attack = attacker.attack(trump);
            System.out.println(attacker.getName() + " attacks with " + attack);
            List<Card> cardList = new ArrayList<>();
            cardList.add(attack);
            attacker.hand.remove(attack);

            Card defend = defender.defend(attack, trump);

            someLabel:
            if (defend != null){
                System.out.println(defender.getName() + " defends with " + defend);
                cardList.add(defend);
                defender.hand.remove(defend);

                System.out.println();
                System.out.println(attacker.getName() + " has " + attacker.hand.size() + " cards. " +
                        defender.getName() + " has " + defender.hand.size() + " cards");
                System.out.println();

                attack = attacker.tossCard(cardList);
                while (attack != null) {
                    cardList.add(attack);
                    attacker.hand.remove(attack);
                    System.out.println(attacker.getName() + " attacks with " + attack);
                    defend = defender.defend(attack, trump);

                    if (defend != null) {
                        System.out.println(defender.getName() + " defends with " + defend);
                        cardList.add(defend);
                        defender.hand.remove(defend);

                        System.out.println();
                        System.out.println(attacker.getName() + " has " + attacker.hand.size() + " cards. " +
                                defender.getName() + " has " + defender.hand.size() + " cards");
                        System.out.println();
                    } else {
                        System.out.println(defender.getName() + " decided to get the cards ");
                        defender.hand.remove(attack);

                        Card tossCard = attacker.tossCard(cardList);
                        while (tossCard!= null && defender.hand.size() <= startSize * 2){
                            System.out.println(attacker.getName() + " decided to toss " +
                                    tossCard.getRank().getValue() + " of " + tossCard.getSuit().getName()

                            );
                            defender.hand.add(tossCard);
                            attacker.hand.remove(tossCard);

                            System.out.println();
                            System.out.println(attacker.getName() + " has " + attacker.hand.size() + " cards. " +
                                    defender.getName() + " has " + defender.hand.size() + " cards");
                            System.out.println();
                            tossCard = attacker.tossCard(cardList);
                        }
                        defender.hand.addAll(cardList);

                        break someLabel;
                    }
                    attack = attacker.tossCard(cardList);
                }
                Player tmp = attacker;
                attacker = defender;
                defender = tmp;

            } else {
                System.out.println(defender.getName() + " decided to get the card ");

                Card tossCard = attacker.tossCard(cardList);
                System.out.println();
                while (tossCard != null && defender.hand.size() <= startSize * 2){
                    System.out.println(attacker.getName() + " decided to toss " +
                            tossCard.getRank().getValue() + " of " + tossCard.getSuit().getName()

                    );
                    cardList.add(tossCard);
                    attacker.hand.remove(tossCard);
                    System.out.println(attacker.getName() + " has " + attacker.hand.size() + " cards. " +
                            defender.getName() + " has " + defender.hand.size() + " cards");
                    System.out.println();
                    tossCard = attacker.tossCard(cardList);
                }

                defender.hand.addAll(cardList);

            }

            if(deck.returnSize() == 0){
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
            System.out.println(player2.getName() + " has " + p2smallest.toString());
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
