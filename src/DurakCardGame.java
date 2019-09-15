
import java.util.Scanner;

public class DurakCardGame {
    public static void main(String[] args) {

        System.out.println("What is your name?");
        String playerName = new Scanner(System.in).nextLine();
        String answer;
        do {
            System.out.println();
            new Game(playerName);
            System.out.println("Do you want to play again? (y/n)");
                    answer = new Scanner(System.in).nextLine().trim();
                    while (!answer.equalsIgnoreCase("y") || !answer.equalsIgnoreCase("n")){
                        System.out.println("Incorrect input. Try again");
                        answer = new Scanner(System.in).nextLine().trim();
                    }
        } while (answer.equalsIgnoreCase("y"));

    }
}
