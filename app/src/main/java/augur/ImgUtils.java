package augur;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Base64;

public class ImgUtils {

    /**
     * Encodes the specified image file as a Base64 string.
     * The image file is expected to be located at "src/main/resources/images/{imgName}.jpg" relative to the project directory.
     *
     * @param imgName the name of the image file (without extension) to encode
     * @return a Base64-encoded string representing the image contents
     * @throws IllegalArgumentException if imgName is null or empty
     * @throws IOException if the image file cannot be read
     */
    public static String getEncodedImgString(String imgName) {
        // Verify valid imgName
        if(imgName.isEmpty() || imgName == null) {
            throw new IllegalArgumentException("Invalid image name.");
        }

        // Get path to image
        String img = System.getProperty("user.dir") + "/src/main/resources/images/" + imgName + ".jpg";
        String encodedImgString = "";

        // Encode image in base 64
        try {
            byte[] imgAsBytes = Files.readAllBytes(Paths.get(img));
            encodedImgString = Base64.getEncoder().encodeToString(imgAsBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedImgString;
    }

    /**
     * Generates an HTML <img> tag using the provided Base64-encoded image string.
     * If the card is reversed, the image is displayed upside down by applying a 180-degree rotation.
     *
     * @param reversed true if the card is reversed and the image should be rotated; false otherwise
     * @param img the Base64-encoded string representing the image
     * @return an HTML <td> element containing the image tag with appropriate styling
     * @throws IllegalArgumentException if the img argument is null
     */
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
