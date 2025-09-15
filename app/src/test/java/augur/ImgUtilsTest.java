package augur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ImgUtilsTest {

    @Test
    public void test_getImgHTMLTag_upright() {
        String result = ImgUtils.getImgHTMLTag(false, "example.jpg");
        assertEquals(result,
                    " <td> <img src=\"data:image/jpg;base64," + "example.jpg" + "\" style=\"width:150px; height:auto;\"> </td>",
                    "test_getImgHTMLTag_upright FAILED");
    }

    @Test
    public void test_getImgHTMLTag_reversed() {
        String result = ImgUtils.getImgHTMLTag(true, "example.jpg");
        assertEquals(result,
                    " <td> <img src=\"data:image/jpg;base64," + "example.jpg" + "\" style=\"width:150px; height:auto; transform: rotate(180deg);\"> </td>",
                    "test_getImgHTMLTag_reversed FAILED");
    }

    @Test
    public void test_getImgHTMLTag_null() {
        assertThrows(IllegalArgumentException.class, () -> ImgUtils.getImgHTMLTag(false, null));
    }
}
