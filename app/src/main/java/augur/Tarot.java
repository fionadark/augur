package augur;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.Callable;
import com.fasterxml.jackson.databind.ObjectMapper;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.awt.Desktop;

@Command(name = "tarot", mixinStandardHelpOptions = true, version = "augur 1.0", description = "divines the future")

public class Tarot implements Callable<String> {

    // global scanner to handle all user input
    public static final Scanner SCANNER = new Scanner(System.in);

    @Option(names = { "-t", "--tarot" }, description = "divines the future")

    /**
     * Prompts the user to perform another tarot reading.
     * If the user chooses to continue, a new reading is started by creating a new Tarot instance and invoking its call method.
     * If the user chooses not to continue, the program ends gracefully.
     * @throws IllegalArgumentException if the user input is invalid.
     */
    public void beginNewReading() {
        String answer;

        System.out.print("Would you like another reading? Enter Y/N: ");
        if (SCANNER.hasNextLine())
            answer = SCANNER.nextLine();
        else
            answer = "N"; // rather than throw an error

        // to start another reading, call .call() on a new instance of Tarot
        if (answer.equals("Y") || answer.equals("Yes")) {
            Tarot t = new Tarot();
            t.call();
            // end program if user chooses no
        } else if (answer.equals("N") || answer.equals("No")) {
            System.out.println("\nOkay, goodbye!\n");
            // throw an exception for weird input
        } else {
            throw new IllegalArgumentException("That wasn't an option, sorry!");
        }

    }

    /**
     * Generates a random boolean value indicating if a card is reversed.
     * There is a 30% chance that the card is reversed (true) and a 70% chance that it is not reversed (false).
     * @return true if the card is reversed, false otherwise
     */
    public static boolean reverseTrueOrFalse() {
        Random rand = new Random();
        int min = 1, max = 10;

        int chance = rand.nextInt(max - min + 1) + min;

        return chance <= 3;
    }

    /**
     * Displays the results of a tarot reading to the command line.
     * The output varies depending on the type of spread chosen (one-card, three-card, or ten-card/celtic cross).
     * 
     * @param curSpread   the Spread object containing the cards and their meanings for the reading
     * @param userChoice  a String representing the user's selected spread type (e.g., "1", "3", or "10")
     * @throws IllegalArgumentException if the userChoice does not correspond to a valid spread
     */
    public void tellFuture(Spread curSpread, String userChoice) {

        // if the user has chosen the celtic cross...
        if(userChoice.contains("10")) {
            System.out.println("Ten cards present themselves to you...\n");
            System.out.println("Your present: " + curSpread.getCards().get(0).getName() + "\nThis card represents: " + curSpread.getCards().get(0).getMeaningUp() + "\n");
            System.out.println("Your current challenge: " + curSpread.getCards().get(1).getName() + "\nThis card represents: " + curSpread.getCards().get(1).getMeaningUp() + "\n");
            System.out.println("Your past: " + curSpread.getCards().get(2).getName() + "\nThis card represents: " + curSpread.getCards().get(2).getMeaningUp() + "\n");
            System.out.println("Your future: " + curSpread.getCards().get(3).getName() + "\nThis card represents: " + curSpread.getCards().get(3).getMeaningUp() + "\n");
            System.out.println("Your conscious: " + curSpread.getCards().get(4).getName() + "\nThis card represents: " + curSpread.getCards().get(4).getMeaningUp() + "\n");
            System.out.println("Your subconscious: " + curSpread.getCards().get(5).getName() + "\nThis card represents: " + curSpread.getCards().get(5).getMeaningUp() + "\n");
            System.out.println("The cards' advice: " + curSpread.getCards().get(6).getName() + "\nThis card represents: " + curSpread.getCards().get(6).getMeaningUp() + "\n");
            System.out.println("Your external influences: " + curSpread.getCards().get(7).getName() + "\nThis card represents: " + curSpread.getCards().get(7).getMeaningUp() + "\n");
            System.out.println("Your hopes and fears: " + curSpread.getCards().get(8).getName() + "\nThis card represents: " + curSpread.getCards().get(8).getMeaningUp() + "\n");
            System.out.println("Your outcome: " + curSpread.getCards().get(9).getName() + "\nThis card represents: " + curSpread.getCards().get(9).getMeaningUp() + "\n");
        // if the user has chosen a three-card draw...
        } else if (userChoice.contains("3")) {
            System.out.println("Three cards present themselves to you...\n");
            System.out.println("Your past: " + curSpread.getCards().get(0).getName());
            System.out.println(curSpread.getCards().get(0).getMeaningRev() + "\n");
            System.out.println("Your present: " + curSpread.getCards().get(1).getName());
            System.out.println(curSpread.getCards().get(1).getMeaningRev() + "\n");
            System.out.println("Your future: " + curSpread.getCards().get(2).getName());
            System.out.println(curSpread.getCards().get(2).getMeaningRev() + "\n");
        // if the user has chosen a one-card draw...
        } else if (userChoice.contains("1")) {
            System.out.println("The card that presents itself to you is " + curSpread.getCards().get(0).getName() + "\n");
            System.out.println("This card represents: " + curSpread.getCards().get(0).getMeaningRev() + "\n");
        // if the user has entered something else
        } else {
            System.out.println("No cards present themselves to you. Your future remains murky!\n");
            throw new IllegalArgumentException("invalid user input");
        }

    }

    /**
     * Prompts the user to select a tarot spread, either by choosing directly or by answering guiding questions.
     * Based on user input, this method appends the appropriate number of cards to the provided URL string to form a complete API request.
     * The supported spreads are: one-card, three-card, and ten-card (Celtic Cross).
     * 
     * @param URLStarter the base URL string to which the number of cards for the selected spread will be appended
     * @return the final URL string representing the user's chosen tarot spread
     * @throws IllegalArgumentException if the user input does not correspond to a valid spread selection
     */
    public String selectTarotSpread(String URLStarter) {
        String userInput;
        boolean spreadSelected = false;

        // ask user if they want to pick their own spread or have augur choose for them
        System.out.println(
                "\nTo begin, would you like to choose your own tarot spread or allow Augur to choose one for you?");
        System.out.println("1) Choose my own.");
        System.out.println("2) Allow Augur to choose.");
        System.out.print("Enter the # of your choice: ");
        userInput = SCANNER.nextLine();

        // option 1: allow user to choose their own tarot spread
        if (userInput.contains("1")) {

            System.out.println("\nExcellent! Select the type of reading you would like, and prepare to look beyond the veil...\n");
            System.out.println("1) The One-Card Spread \nPerfect if you have a specific question you want answered!\n");
            System.out.println("2) The Three-Card Spread \nA simple but insightful reading of your past, present, and future.\n");
            System.out.println("3) The Celtic Cross \nA complex reading representing the many aspects of your life. Peer into your future, if you dare...\n");
            System.out.print("Enter the # of your choice: ");
            userInput = SCANNER.nextLine();

            if (userInput.contains("1"))
                URLStarter += "1"; // one card spread
            else if (userInput.contains("2"))
                URLStarter += "3"; // three card spread
            else if (userInput.contains("3"))
                URLStarter += "10"; // celtic cross (10 card spread)
            else
                URLStarter += "0";

        // option 2: provide guiding questions to choose type of tarot spread for the user
        } else if (userInput.contains("2")) {

            System.out.println("\nExcellent! Use your inner eye to choose your answers to a few guiding questions, and Augur can select a tarot spread for you.");
            System.out.println("Do you want a simple or complex reading?");
            System.out.println("1) Simple.");
            System.out.println("2) Complex.");
            System.out.print("Enter the # of your choice: ");
            userInput = SCANNER.nextLine();

            // for a simple reading, give user the one-card spread
            if (userInput.contains("1") && spreadSelected == false) {
                URLStarter += "1";
                spreadSelected = true;
            }

            System.out.println("\nWould you like to focus on how your past connects to your future, or just on your future?");
            System.out.println("1) The past and present.");
            System.out.println("2) Just the future.");
            System.out.print("Enter the # of your choice: ");
            userInput = SCANNER.nextLine();

            // for a reading of both past and present, give user the three-card spread
            if (userInput.contains("1") && spreadSelected == false) {
                URLStarter += "3";
                spreadSelected = true;
            }

            System.out.println("\nDo you want a general reading, or do you want to focus on one aspect of your future?");
            System.out.println("1) General reading.");
            System.out.println("2) Specific reading.");
            System.out.print("Enter the # of your choice: ");
            userInput = SCANNER.nextLine();

            // as default, give reader the celtic cross spread (10 cards)
            if(spreadSelected == false) {
                URLStarter += "10";
            }

        } else {
            throw new IllegalArgumentException("Invalid input.");
        }

        return URLStarter;
    }

    /**
     * Executes a tarot reading session from the command line.
     * This method prompts the user to select a tarot spread, retrieves card data from the Tarot API,
     * displays the reading results, writes the reading to an HTML file, and opens the file in the default browser.
     * At the end of the session, the user is prompted to perform another reading or exit.
     * 
     * @return an empty String after the reading session completes
     * @throws RuntimeException if the Tarot API connection fails
     * @throws IllegalArgumentException if the user input is invalid during spread selection or reading prompts
     */
    @Override
    public String call() {

        // welcome message
        System.out.println("\nWelcome to Augur: The Free Tarot Tool!");
        System.out.println("This is a command line interface designed to provide an authentic tarot card reading experience.");

        // select tarot spread
        String URLStarter = "https://tarotapi.dev/api/v1/cards/random?n=";
        String URLString = selectTarotSpread(URLStarter);
        System.out.println("\nReading the portents... Consulting the auguries... \n");

        int equalsIndex = URLString.indexOf("=");
        String userChoice = URLString.substring(equalsIndex + 1);

        // establish connection to Tarot API URL
        try {
            URI u = new URI(URLString);
            URL url = u.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // check if connection was successful before continuing!
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("api connection failure");
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                // write data into one big string
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                // read data from String inline into Spread currentSpread
                ObjectMapper mapper = new ObjectMapper();
                Spread currentSpread = mapper.readValue(inline, Spread.class);

                // call tellFuture with currentSpread to print out the cards
                tellFuture(currentSpread, userChoice);

                // close the secondary scanner
                scanner.close();

                // get url of index.html and make sure it exists
                File f = new File(System.getProperty("user.home") + "/index.html");

                // get absolute path of file
                String path = f.getAbsolutePath();

                // get list of the short_names of currentSpread
                List<String> currentNames = currentSpread.getShortNames();

                // write html tag into index.html for each card in currentNames/currentSpread
                FileUtils.writeToFile(currentSpread, path, currentNames);

                // open index.html
                Desktop.getDesktop().open(f);
            }

        } catch (URISyntaxException | IOException exception) {
            exception.printStackTrace();
        }

        // check if user wants another reading
        beginNewReading();

        return "";
    }
}