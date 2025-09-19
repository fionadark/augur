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

    // Global scanner for user input
    public static Scanner SCANNER = new Scanner(System.in);

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
            answer = "N";

        if (answer.equals("Y") || answer.equals("Yes")
        || answer.equals("y") || answer.equals("yes")) {
            // To start another reading, invoke call method on new Tarot instance
            Tarot t = new Tarot();
            t.call();
        } else if (answer.equals("N") || answer.equals("No")
        || answer.equals("n") || answer.equals("no")) {
            // To end the program, simply exit
            System.out.println("\nOkay, goodbye!\n");
        } else {
            // Throw an exception for invalid input
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
     * @param currentSpread   the Spread object containing the cards and their meanings for the reading
     * @param userChoice  a String representing the user's selected spread type (e.g., "1", "3", or "10")
     * @throws IllegalArgumentException if the userChoice does not correspond to a valid spread
     */
    public void tellFuture(Spread currentSpread, String userChoice) {

        // Print results of a celtic cross (10-card) spread
        if(userChoice.contains("10")) {
            System.out.println("Ten cards present themselves to you...\n");
            System.out.println("Your present: " + currentSpread.getCards().get(0).getName() + "\nThis card represents: " + currentSpread.getCards().get(0).getMeaningUp() + "\n");
            System.out.println("Your current challenge: " + currentSpread.getCards().get(1).getName() + "\nThis card represents: " + currentSpread.getCards().get(1).getMeaningUp() + "\n");
            System.out.println("Your past: " + currentSpread.getCards().get(2).getName() + "\nThis card represents: " + currentSpread.getCards().get(2).getMeaningUp() + "\n");
            System.out.println("Your future: " + currentSpread.getCards().get(3).getName() + "\nThis card represents: " + currentSpread.getCards().get(3).getMeaningUp() + "\n");
            System.out.println("Your conscious: " + currentSpread.getCards().get(4).getName() + "\nThis card represents: " + currentSpread.getCards().get(4).getMeaningUp() + "\n");
            System.out.println("Your subconscious: " + currentSpread.getCards().get(5).getName() + "\nThis card represents: " + currentSpread.getCards().get(5).getMeaningUp() + "\n");
            System.out.println("The cards' advice: " + currentSpread.getCards().get(6).getName() + "\nThis card represents: " + currentSpread.getCards().get(6).getMeaningUp() + "\n");
            System.out.println("Your external influences: " + currentSpread.getCards().get(7).getName() + "\nThis card represents: " + currentSpread.getCards().get(7).getMeaningUp() + "\n");
            System.out.println("Your hopes and fears: " + currentSpread.getCards().get(8).getName() + "\nThis card represents: " + currentSpread.getCards().get(8).getMeaningUp() + "\n");
            System.out.println("Your outcome: " + currentSpread.getCards().get(9).getName() + "\nThis card represents: " + currentSpread.getCards().get(9).getMeaningUp() + "\n");
        // Print results of a three-card spread
        } else if (userChoice.contains("3")) {
            System.out.println("Three cards present themselves to you...\n");
            System.out.println("Your past: " + currentSpread.getCards().get(0).getName());
            System.out.println(currentSpread.getCards().get(0).getMeaningRev() + "\n");
            System.out.println("Your present: " + currentSpread.getCards().get(1).getName());
            System.out.println(currentSpread.getCards().get(1).getMeaningRev() + "\n");
            System.out.println("Your future: " + currentSpread.getCards().get(2).getName());
            System.out.println(currentSpread.getCards().get(2).getMeaningRev() + "\n");
        // Print results of a one-card spread
        } else if (userChoice.contains("1")) {
            System.out.println("The card that presents itself to you is " + currentSpread.getCards().get(0).getName() + "\n");
            System.out.println("This card represents: " + currentSpread.getCards().get(0).getMeaningRev() + "\n");
        // Handle invalid input
        } else {
            System.out.println("No cards present themselves to you. Your future remains murky!\n");
            throw new IllegalArgumentException("invalid user input");
        }

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

        // Welcome message
        System.out.println("\nWelcome to Augur: The Free Tarot Tool!");
        System.out.println("This is a command line interface designed to provide an authentic tarot card reading experience.");

        // Build URL for Tarot API request based on user's selected spread
        String URLStarter = "https://tarotapi.dev/api/v1/cards/random?n=";
        String URLString = Spread.selectTarotSpread(SCANNER, URLStarter);
        System.out.println("\nReading the portents... Consulting the auguries... \n");

        int equalsIndex = URLString.indexOf("=");
        String userChoice = URLString.substring(equalsIndex + 1);

        // Make GET request to Tarot API and read response
        try {
            URI u = new URI(URLString);
            URL url = u.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Verify connection is successful
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("api connection failure");
            } else {

                // Read data from API into String and convert JSON response to Spread object using Jackson
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                // Read data from API
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                // Convert JSON response into Spread object
                ObjectMapper mapper = new ObjectMapper();
                Spread currentSpread = mapper.readValue(inline, Spread.class);

                // Display results of tarot reading to command line
                tellFuture(currentSpread, userChoice);

                // Connection cleanup
                scanner.close();

                // Write results of tarot reading to index.html and open in browser
                File f = new File(System.getProperty("user.home") + "/index.html");
                String path = f.getAbsolutePath();
                List<String> currentNames = currentSpread.getShortNames();
                FileUtils.writeToFile(currentSpread, path, currentNames);
                Desktop.getDesktop().open(f);
            }

        } catch (URISyntaxException | IOException exception) {
            exception.printStackTrace();
        }

        // Prompt user to begin a new reading or exit
        beginNewReading();

        return "";
    }
}