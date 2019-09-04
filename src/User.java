import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class User extends Player {

    public User(String name, List<Card> hand) {
        super(name, hand);
    }

    @Override
    public Card attack(Suit trump) {
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
                hand.add(againstCard);
                return null;
            } else {
                try {
                    while (Integer.parseInt(answer) <= 0 || Integer.parseInt(answer) > hand.size()) {
                        System.out.println("Incorrect answer. Try again: ");
                        answer = new Scanner(System.in).nextLine().trim();
                    }
                    Card answerCard = hand.get(Integer.parseInt(answer) - 1);
                    if (answerCard.beats(againstCard, trump)) {
                        hand.remove(Integer.parseInt(answer) - 1);
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
}
