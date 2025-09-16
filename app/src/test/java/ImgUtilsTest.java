import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import augur.ImgUtils;
import org.junit.jupiter.api.Test;

public class ImgUtilsTest {

    @Test
    public void test_getImgHTMLTag_upright() {
        String result = ImgUtils.getImgHTMLTag(false, "example.jpg");
        assertEquals(result,
                " <td> <img src=\"data:image/jpg;base64," + "example.jpg"
                        + "\" style=\"width:150px; height:auto;\"> </td>",
                "test_getImgHTMLTag_upright FAILED");
    }

    @Test
    public void test_getImgHTMLTag_reversed() {
        String result = ImgUtils.getImgHTMLTag(true, "example.jpg");
        assertEquals(result,
                " <td> <img src=\"data:image/jpg;base64," + "example.jpg"
                        + "\" style=\"width:150px; height:auto; transform: rotate(180deg);\"> </td>",
                "test_getImgHTMLTag_reversed FAILED");
    }

    @Test
    public void test_getImgHTMLTag_null() {
        assertThrows(IllegalArgumentException.class, () -> ImgUtils.getImgHTMLTag(false, null));
    }

    @Test
    public void test_getEncodedImgString_valid() throws Exception {
        String imgPath = System.getProperty("user.dir") + "/src/main/resources/images/swqu.jpg";
        byte[] imgBytes = Files.readAllBytes(Paths.get(imgPath));
        String expectedBase64 = Base64.getEncoder().encodeToString(imgBytes);

        String actualBase64 = ImgUtils.getEncodedImgString("swqu");

        assertEquals(expectedBase64, actualBase64);
    }

    @Test
    public void test_getEncodedImgString_invalid() {
        assertThrows(IllegalArgumentException.class, () -> ImgUtils.getEncodedImgString(""));
    }
}
