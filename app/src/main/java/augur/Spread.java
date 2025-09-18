package augur;

import java.util.*;

public class Spread {
    public int nhits;
    public List<Card> cards;

    public Spread() {
    }

    public Spread(int nhits, List<Card> cards) {
        this.nhits = nhits;
        this.cards = cards;
    }

    // Accessor methods

    public int getNHits() {
        return nhits;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<String> getShortNames() {
        List<String> names = new ArrayList<String>();

        for (int i = 0; i < this.nhits; i++) {
            names.add(this.cards.get(i).getNameShort());
        }
        return names;

    }

    /**
     * Prompts the user to select a tarot spread, either by choosing directly or by
     * answering guiding questions.
     * Based on user input, this method appends the appropriate number of cards to
     * the provided URL string to form a complete API request.
     * The supported spreads are: one-card, three-card, and ten-card (Celtic Cross).
     * 
     * @param URLStarter the base URL string to which the number of cards for the
     *                   selected spread will be appended
     * @return the final URL string representing the user's chosen tarot spread
     * @throws IllegalArgumentException if the user input does not correspond to a
     *                                  valid spread selection
     */
    public static String selectTarotSpread(Scanner SCANNER, String URLStarter) {
        String userInput;
        boolean spreadSelected = false;

        // Prompt user to choose tarot spread
        System.out.println(
                "\nTo begin, would you like to choose your own tarot spread or allow Augur to choose one for you?");
        System.out.println("1) Choose my own.");
        System.out.println("2) Allow Augur to choose.");
        System.out.print("Enter the # of your choice: ");
        userInput = SCANNER.nextLine();

        if (userInput.contains("1")) {
            URLStarter = userSelfSelect(SCANNER, URLStarter);
        } else if (userInput.contains("2")) {

            // First, ask if the user wants a simple or complex reading
            spreadSelected = selectOneCardSpread(SCANNER);
            if (spreadSelected) {
                URLStarter += "1";
            }

            // Second, ask if the user wants to focus on the future or learn about the past/present
            if (!spreadSelected) {
                spreadSelected = selectThreeCardSpread(SCANNER);
                if (spreadSelected) {
                    URLStarter += "3";
                }
            } else {
                spreadSelected = selectThreeCardSpread(SCANNER);
            }

            // Third, ask if the user wants a general or specific reading
            if (!spreadSelected) {
                spreadSelected = selectTenCardSpread(SCANNER);
                if (spreadSelected) {
                    URLStarter += "10";
                }
            } else {
                spreadSelected = selectTenCardSpread(SCANNER);
            }

            // Provide default result (10 cards) if the user gave contradictory answers
            if (!spreadSelected) {
                URLStarter += "10";
            }

        } else {
            throw new IllegalArgumentException("Invalid input.");
        }

        return URLStarter;
    }

    /**
     * Prompts the user to self select a Tarot spread type from a list of options.
     * @param SCANNER the Scanner object used to read user input from the command line
     * @param URLStarter the base URL string to which the number of cards for the selected spread will be appended
     * @return the final URL string
     */
    public static String userSelfSelect(Scanner SCANNER, String URLStarter) {
        String userInput;

        System.out.println(
                "\nExcellent! Select the type of reading you would like, and prepare to look beyond the veil...\n");
        System.out.println("1) The One-Card Spread \nPerfect if you have a specific question you want answered!\n");
        System.out.println(
                "2) The Three-Card Spread \nA simple but insightful reading of your past, present, and future.\n");
        System.out.println(
                "3) The Celtic Cross \nA complex reading representing the many aspects of your life. Peer into your future, if you dare...\n");
        System.out.print("Enter the # of your choice: ");
        
        userInput = SCANNER.nextLine();

        if (userInput.contains("1")) {
            URLStarter += "1";
        } else if (userInput.contains("2")) {
            URLStarter += "3";
        } else if (userInput.contains("3")) {
            URLStarter += "10";
        } else {
            URLStarter += "0";
        }

        return URLStarter;

    }

    /**
     * Prompts the user to choose between a simple or complex tarot reading (one-card or not).
     * @param SCANNER the Scanner object used to read user input from the command line
     * @return true if the user selects a simple reading (one-card spread); false otherwise
     */
    public static boolean selectOneCardSpread(Scanner SCANNER) {
        String userInput;
        boolean userHasAnswered = false;

        while (!userHasAnswered) {
            System.out.println(
                    "\nWould you like a simple or complex reading?");
            System.out.println("1) Simple.");
            System.out.println("2) Complex.");
            System.out.print("Enter the # of your choice: ");
            
            userInput = SCANNER.nextLine();

            if (userInput.contains("1")) {
                return true;
            } else if (userInput.contains("2")) {
                return false;
            } else {
                System.out.println("\nInvalid input. Please try again.");
            }
        }

        return true;
    }

    /**
     * Prompts the user to choose the focus of their reading (three-card or not).
     * @param SCANNER the Scanner object used to read user input from the command line
     * @return true if the user selects to focus on the past and present (three-card spread); false if otherwise
     */
    public static boolean selectThreeCardSpread(Scanner SCANNER) {
        String userInput;
        boolean userHasAnswered = false;

        while (!userHasAnswered) {
            System.out.println(
                    "\nWould you like to focus on how your past connects to your future, or just on your future?");
            System.out.println("1) The past and present.");
            System.out.println("2) Just the future.");
            System.out.print("Enter the # of your choice: ");
            
            userInput = SCANNER.nextLine();

            if (userInput.contains("1")) {
                return true;
            } else if (userInput.contains("2")) {
                return false;
            } else {
                System.out.println("\nInvalid input. Please try again.");
            }
        }

        return true;
    }

    /**
     * Prompts the user to choose between a general tarot reading or a specific
     * focus on one aspect of their future.
     * @param SCANNER the Scanner object used to read user input from the command line
     * @return true if the user selects a general reading (ten-card spread); false if otherwise
     */
    public static boolean selectTenCardSpread(Scanner SCANNER) {
        String userInput;
        boolean userHasAnswered = false;

        while (!userHasAnswered) {
            System.out.println(
                    "\nDo you want a general reading, or do you want to focus on one aspect of your future?");
            System.out.println("1) General reading.");
            System.out.println("2) Specific reading.");
            System.out.print("Enter the # of your choice: ");
            
            userInput = SCANNER.nextLine();

            if (userInput.contains("1")) {
                return true;
            } else if (userInput.contains("2")) {
                return false;
            } else {
                System.out.println("\nInvalid input. Please try again.");
            }
        }

        return true;
    }

}
