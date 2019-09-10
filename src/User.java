import java.util.Comparator;
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
        int i = new Scanner(System.in).nextInt() - 1;
        Card card = hand.get(i);
        hand.remove(i);
        return card;
    }

    @Override
    public Card defend(Card againstCard, Suit trump) {
        System.out.println("Choose card to defend or enter 'get' to get the card");
        int j = 1;
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(j + ". " + hand.get(i));
            j++;
        }
        while (true) {
            String answer = new Scanner(System.in).nextLine().trim();
            if (answer.equalsIgnoreCase("get")) {
                return null;
            } else {
                try {
                    while (Integer.parseInt(answer) <= 0 || Integer.parseInt(answer) > hand.size()) {
                        System.out.println("Incorrect answer. Try again: ");
                        answer = new Scanner(System.in).nextLine().trim();
                    }
                    Card answerCard = hand.get(Integer.parseInt(answer) - 1);
                    if (answerCard.beats(againstCard, trump)) {
                        return answerCard;
                    } else {
                        System.out.println("You can't defend with this card, choose another one");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect answer. Try again: ");
                }

            }
        }
    }

    public Card tossCard(List<Card> cardList) {
        System.out.println("Choose a card to toss or enter 'not' to finish the round: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i));
        }
        while (true) {
            String answer = new Scanner(System.in).nextLine().trim();
            someLabel:
            if (answer.equalsIgnoreCase("not")) {
                return null;
            } else {
                try {
                    int cardIndex = Integer.parseInt(answer);
                    while (cardIndex <= 0 || cardIndex > hand.size()) {
                        System.out.println("Incorrect answer1. Try again: ");
                        answer = new Scanner(System.in).nextLine().trim();
                        cardIndex = Integer.parseInt(answer);
                    }
                    Card answerCard = hand.get(cardIndex - 1);
                    while (true) {
                        for (Card card : cardList) {
                            if (answerCard.getRank().getValue() == card.getRank().getValue()) {
                                return answerCard;
                            }
                        }
                        System.out.println("Incorrect answer2. Try again: ");
                        break someLabel;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect answer3. Try again: ");
                }
            }
        }
    }
}
