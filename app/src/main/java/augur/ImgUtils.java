package augur;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Base64;

public class ImgUtils {

    // getEncodedImgString() will encode a given image in base 64
    // returns encoded img as a String
    public static String getEncodedImgString(String imgName) {
        // verify valid imgName
        if(imgName.isEmpty() || imgName == null) {
            System.out.println("Invalid image request.");
            return "";
        }

        // path to image should be : userDir/src/main/resources/images/imgName.jpg
        String img = System.getProperty("user.dir") + "/src/main/resources/images/" + imgName + ".jpg";
        String encodedImgString = "";

        // encode image in base 64
        try {
            byte[] imgAsBytes = Files.readAllBytes(Paths.get(img));
            encodedImgString = Base64.getEncoder().encodeToString(imgAsBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedImgString;
    }

    // getImgHTMLTag() creates and returns an HTML tag with the given image string
    // if the card is reversed, the img tag includes "transform: rotate(160deg)" so image will display upside down
    public static String getImgHTMLTag(boolean reversed, String img) {
        String tag = "";

        // check for invalid arguments
        if (img == null)
            throw new IllegalArgumentException("null argument");

        // if the card is reversed, alter img tag to flip the image 180 degrees
        if (reversed)
            tag = " <td> <img src=\"data:image/jpg;base64," + img
                    + "\" style=\"width:150px; height:auto; transform: rotate(180deg);\"> </td>";
        else
            tag = " <td> <img src=\"data:image/jpg;base64," + img + "\" style=\"width:150px; height:auto;\"> </td>";

        return tag;
    }
    
}
