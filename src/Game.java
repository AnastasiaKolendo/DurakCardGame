import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game {
    private String playerName;
    private Player attacker;
    private Player defender;

    Game(String name){
        playerName = name;
        run();
    }

    private void run() {
        Deck deck = Deck.createShuffled();
        Player player1 = new User(playerName, deck.dealCard(6));
        Player player2 = new Computer("Computer", deck.dealCard(6));

        Card bottom = deck.getBottom();
        Suit trump = bottom.getSuit();
        System.out.println("Trump is " + trump.getName());

        attacker = findAttacker(player1, player2, trump);
        if (attacker == player1) {
            defender = player2;
        } else {
            defender = player1;
        }

        while (true){
            addCards(deck, attacker);
            addCards(deck, defender);
            int startSize = defender.hand.size();
            showNumberOfCards();

            attacker.sortCards(trump);
            defender.sortCards(trump);
            if (deck.returnSize() > 0) {
                System.out.println("The remaining number of cards in the deck is " + deck.returnSize());
                System.out.println();
            } else {
                System.out.println("There are no more cards in the deck.");
                System.out.println();
            }
            List<Card> cardList = new ArrayList<>();
            Card attack = attacker.attack(trump);
            System.out.println(attacker.getName() + " attacks with " + attack);
            attacker.hand.remove(attack);
            cardList.add(attack);
            Card defend = defender.defend(attack, trump);

            someLabel:
            if (defend != null){
                System.out.println(defender.getName() + " defends with " + defend);
                cardList.add(defend);
                defender.hand.remove(defend);

                showNumberOfCards();

                attack = attacker.tossCard(cardList);
                while (attack != null) {
                    attacker.hand.remove(attack);
                    cardList.add(attack);
                    System.out.println(attacker.getName() + " attacks with " + attack);
                    defend = defender.defend(attack, trump);

                    if (defend != null) {
                        System.out.println(defender.getName() + " defends with " + defend);
                        cardList.add(defend);
                        defender.hand.remove(defend);

                        showNumberOfCards();
                    } else {
                        getCards(startSize, attack, cardList);
                        break someLabel;
                    }
                    attack = attacker.tossCard(cardList);
                }
                Player tmp = attacker;
                attacker = defender;
                defender = tmp;
            } else {
                getCards(startSize, attack, cardList);
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

    private void getCards(int startSize, Card attack, List<Card> cardList) {
        System.out.println(defender.getName() + " decided to get the cards ");
        defender.hand.remove(attack);

        Card tossCard = attacker.tossCard(cardList);
        System.out.println();
        while (tossCard!= null && defender.hand.size() <= startSize * 2){
            System.out.println(attacker.getName() + " decided to toss " +
                    tossCard.getRank().getValue() + " of " + tossCard.getSuit().getName()

            );
            cardList.add(tossCard);
            attacker.hand.remove(tossCard);

            showNumberOfCards();
            tossCard = attacker.tossCard(cardList);
        }
        defender.hand.addAll(cardList);
    }

    private void showNumberOfCards() {
        System.out.println();
        System.out.println(attacker.getName() + " has " + attacker.hand.size() + " cards. " +
                defender.getName() + " has " + defender.hand.size() + " cards");
        System.out.println();
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
                System.out.println(player2.getName() + " is first");
                return player2;
            }
        } else if( p1smallest == null){
            System.out.println(player1.getName() + " doesn't have trumps");
            System.out.println(player2.getName() + " is first");
            return player2;
        } else if(p2smallest == null){
            System.out.println(player2.getName() + " doesn't have trumps");
            System.out.println(player1.getName() + " is first");
            return player1;
        } else {
            Rank rank1 = p1smallest.getRank();
            Rank rank2 = p2smallest.getRank();
            System.out.print(player1.getName() + " has " + p1smallest.toString() + ". ");
            System.out.println(player2.getName() + " has " + p2smallest.toString());
            if (rank1.getValue() > rank2.getValue()){
                System.out.println(player2.getName() + " is first");
                return player2;
            } else {
                System.out.println(player1.getName() + " is first");
                return player1;
            }
        }
    }


}
