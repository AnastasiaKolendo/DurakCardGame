import java.util.List;
import java.util.Scanner;

public class User extends Player {

    public User(String name, List<Card> hand) {
        super(name, hand);
    }

    @Override
    public Card attack(Suit trump) {
        System.out.println("Choose a card to attack:");
        int j = 1;
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(j + ". " + hand.get(i));
            j++;
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
        while (true){
            String answer = new Scanner(System.in).nextLine().trim();
            if (answer.equalsIgnoreCase("get")){
                hand.add(againstCard);
                return null;
            } else {
                Card answerCard = hand.get(Integer.parseInt(answer) - 1);
                if (answerCard.beats(againstCard, trump)){
                    hand.remove(Integer.parseInt(answer) - 1);
                    return answerCard;
                } else {
                    System.out.println("You can't defend with this card, choose another one");
                }
            }
        }
    }
}
