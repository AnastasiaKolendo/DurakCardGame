import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User extends Player {

    public User(String name, List<Card> hand) {
        super(name, hand);
    }

    @Override
    public Card attack(Suit trump) {

        System.out.println("Choose a card to attack:");

        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i));
        }
        while (true) {
            String answer = new Scanner(System.in).nextLine().trim();
            try {
                int i = Integer.parseInt(answer);
                while (i <= 0 || i > hand.size()) {
                    System.out.println("Incorrect answer. Try again: ");
                    answer = new Scanner(System.in).nextLine().trim();
                    i = Integer.parseInt(answer);
                }
                Card card = hand.get(i - 1);
                hand.remove(i - 1);
                return card;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect answer. Try again: ");
            }
        }
    }

    @Override
    public Card defend(Card againstCard, Suit trump) {
        System.out.println("Choose card to defend or enter 'get' to get the card");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i));
        }
        while (true) {
            String answer = new Scanner(System.in).nextLine().trim();
            if (answer.equalsIgnoreCase("get")) {
                return null;
            } else {
                try {
                    int index = Integer.parseInt(answer);
                    while (index <= 0 || index > hand.size()) {
                        System.out.println("Incorrect answer. Try again: ");
                        answer = new Scanner(System.in).nextLine().trim();
                        index = Integer.parseInt(answer);
                    }
                    Card answerCard = hand.get(index - 1);

                    if (answerCard.beats(againstCard, trump)) {
                        return answerCard;
                    } else {
                        System.out.println("You can't defend with this card, choose another one: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect answer. Try again: ");
                }

            }
        }
    }

    public Card tossCard(List<Card> cardList) {
        List<Card> tossCards = new ArrayList<>();
        for (Card cardHand : hand) {
            for (Card card : cardList) {
                if (cardHand.getRank().getValue() == card.getRank().getValue()) {
                    tossCards.add(cardHand);
                    break;
                }
            }
        }
        if(tossCards.size() == 0){
            return null;
        }
        System.out.println("Choose a card to toss or enter 'not' to finish the round: ");
        for (int i = 0; i < tossCards.size(); i++) {
            System.out.println((i+1) + ". " + tossCards.get(i));
        }
        while (true) {
            String answer = new Scanner(System.in).nextLine().trim();
            if (answer.equalsIgnoreCase("not")) {
                return null;
            } else {
                try {
                    int cardIndex = Integer.parseInt(answer);
                    while (cardIndex <= 0 || cardIndex > hand.size()) {
                        System.out.println("Incorrect answer. Try again: ");
                        answer = new Scanner(System.in).nextLine().trim();
                        cardIndex = Integer.parseInt(answer);
                    }
                    Card card = tossCards.get(cardIndex - 1);
                    cardList.remove(card);
                    return tossCards.get(cardIndex - 1);
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect answer. Try again: ");
                }
            }
        }
    }
}
