import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import augur.Card;
import augur.FileUtils;
import augur.ImgUtils;
import augur.Spread;
import augur.Tarot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class FileUtilsTest {

    private final String tempPath = "test_index.html";
    private Card mockCard;
    private Spread mockSpread;

    @BeforeEach
    public void setup() {
        mockCard = mock(Card.class);
        when(mockCard.getNameShort()).thenReturn("MockShort");
        when(mockCard.getCardMeaningAsHTML(anyBoolean())).thenReturn("<td>MockMeaning</td>");

        mockSpread = mock(Spread.class);
        when(mockSpread.getNHits()).thenReturn(1);
        when(mockSpread.getCards()).thenReturn(Arrays.asList(mockCard));
    }

    @AfterEach
    public void cleanup() throws Exception {
        Files.deleteIfExists(Paths.get(tempPath));
    }

    @Test
    @Disabled("for later")
    public void test_writeToFile() throws Exception {
        List<String> curNames = Arrays.asList("MockShort");

        // Mock static methods
        try (
            MockedStatic<Tarot> tarotMock = mockStatic(Tarot.class);
            MockedStatic<ImgUtils> imgUtilsMock = mockStatic(ImgUtils.class);
            MockedStatic<Card> cardStaticMock = mockStatic(Card.class)
        ) {
            tarotMock.when(Tarot::reverseTrueOrFalse).thenReturn(true);
            imgUtilsMock.when(() -> ImgUtils.getEncodedImgString("MockShort")).thenReturn("MockImgString");
            imgUtilsMock.when(() -> ImgUtils.getImgHTMLTag(true, "MockImgString")).thenReturn("<td>MockImgTag</td>");
            cardStaticMock.when(() -> Card.getCardPosName(1, 0)).thenReturn("<td>MockPos</td>");

            // Test writeToFile
            FileUtils.writeToFile(mockSpread, tempPath, curNames);

            // Verify results
            assertTrue(Files.exists(Paths.get(tempPath)));
            String content = Files.readString(Paths.get(tempPath));
            assertTrue(content.contains("<td>MockImgTag</td>"));
            assertTrue(content.contains("<td>MockPos</td>"));
            assertTrue(content.contains("<td>MockMeaning</td>"));
        }
    }
}