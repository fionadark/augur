package augur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileUtils {

    /* writeToFile() creates an HTML tag for each card in a spread to display its meaning, position, and image
     * each HTML tag is included in a HTML table
     * the HTML table is written to index.html to display as a web page
     */
    public static void writeToFile(Spread curSpread, String path, List<String> curNames) {

        // truncate exisiting contents of index.html to 0
        File file = new File(path);
        if (file.exists())
            file.delete();

        // declare strBuilder that will hold values for rows of html table
        StringBuilder strBuilder = new StringBuilder();

        // save each row of the table to strBuilder
        for (int i = 0; i < curSpread.getNHits(); i++) {
            // open new row
            strBuilder.append("<tr>");

            // check if this card should be reversed
            boolean reversed = Tarot.reverseTrueOrFalse();

            // get base 64 encoded path to tarot card image
            String encodedImgString = ImgUtils.getEncodedImgString(curSpread.getCards().get(i).getNameShort());

            // append value for "Your Cards" column of table to strBuilder
            String imgHTMLTag = ImgUtils.getImgHTMLTag(reversed, encodedImgString);
            strBuilder.append(imgHTMLTag);

            // append value for "Position" column of table to strBuilder
            String cardPos = Card.getCardPosName(curSpread.getNHits(), i);
            strBuilder.append(cardPos);

            // append value for "Meaning" column of table to strBuilder
            String cardMeaning = curSpread.getCards().get(i).getCardMeaningAsHTML(reversed);
            strBuilder.append(cardMeaning);

            // close row
            strBuilder.append(" </tr>\n");

        }

        // insert strBuilder into html code that will be written to index.html
        String output = """
                <html>
                    <head>
                        <style>
                            h1 { color:white; text-align:center; padding-top:25px; padding-bottom:25px; }
                            body { background-color:pink; }
                            table { width:55%%; border:0; margin: auto; background-color:white; border-spacing:25px; }
                        </style>
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

        // write to index.html
        try {
            Files.write(Paths.get(path), output.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}
