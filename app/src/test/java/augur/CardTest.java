package augur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CardTest {
    
    @Test
    public void test_getCardMeaningAsHTML_upright() {
        Card test = new Card("cardName", "up_meaning", "rev_meaning");
        boolean reversed = false;

        String expected = " <td><strong>cardName</strong><br><br>This card represents: up_meaning</td>";
        String actual = test.getCardMeaningAsHTML(reversed);

        assertEquals(expected, actual);
    }

    @Test
    public void test_getCardMeaningAsHTML_reversed() {
        Card test = new Card("cardName", "up_meaning", "rev_meaning");
        boolean reversed = true;

        String expected = " <td><strong>cardName</strong><br><br>This card reversed represents: rev_meaning</td>";
        String actual = test.getCardMeaningAsHTML(reversed);

        assertEquals(expected, actual);
    }

}
