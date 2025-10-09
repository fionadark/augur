package augur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileUtils {

    /**
     * Writes the details of a tarot spread to an HTML file for display as a web
     * page.
     * For each card in the spread, this method generates an HTML table row
     * containing the card's image, position, and meaning.
     * The resulting HTML table is inserted into a styled HTML document and saved to
     * the specified file path.
     *
     * @param curSpread the Spread object containing the cards and their meanings to
     *                  be displayed
     * @param path      the file path where the HTML output will be written
     * @param curNames  a list of short names for the cards in the spread
     * @throws IOException if an error occurs while writing to the file
     */
    public static void writeToFile(Spread curSpread, String path, List<String> curNames) {

        // Truncate any existing conntents of index.html
        File file = new File(path);
        if (file.exists())
            file.delete();

        // Declare StringBuilder to hold rows of html table
        StringBuilder strBuilder = new StringBuilder();

        // Save each card's info as a row in the html table
        for (int i = 0; i < curSpread.getNHits(); i++) {
            // Open new row
            strBuilder.append("<tr>");

            // Determine if card is reversed
            boolean reversed = Tarot.reverseTrueOrFalse();

            // Get base 64 encoded image string for card
            String encodedImgString = ImgUtils.getEncodedImgString(curSpread.getCards().get(i).getNameShort());

            // Append value for "Your Cards" column of table to strBuilder
            String imgHTMLTag = ImgUtils.getImgHTMLTag(reversed, encodedImgString);
            strBuilder.append(imgHTMLTag);

            // Append value for "Position" column of table to strBuilder
            String cardPos = Card.getCardPosName(curSpread.getNHits(), i);
            strBuilder.append(cardPos);

            // Append value for "Meaning" column of table to strBuilder
            String cardMeaning = curSpread.getCards().get(i).getCardMeaningAsHTML(reversed);
            strBuilder.append(cardMeaning);

            // Close row
            strBuilder.append(" </tr>\n");

        }

        // Create full HTML output with table rows inserted
        String output = """
                <html>
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Augur</title>
                        <link rel="stylesheet" href="styles.css">
                    </head>
                    <body>
                        <h1>Your future awaits...</h1>
                        <table>
                            <tr>
                                <th>Your Cards</th>
                                <th>Position</th>
                                <th>Meaning</th>
                            </tr>
                            %s
                        </table>
                    </body>
                </html>
                """.formatted(strBuilder.toString());

        // Write output to file
        try {
            Files.write(Paths.get(path), output.getBytes(), StandardOpenOption.CREATE);

            // Copy CSS file to the same directory as the HTML file
            String cssSourcePath = System.getProperty("user.dir") + "/src/main/resources/styles.css";
            String cssDestPath = Paths.get(path).getParent().toString() + "/styles.css";
            Files.copy(Paths.get(cssSourcePath), Paths.get(cssDestPath), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
