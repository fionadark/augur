import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import augur.Card;
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

    @Test
    public void test_getCardPos_1Card() {
        String expected = " <td>Your Future</td>";
        String actual = Card.getCardPosName(1,1);

        assertEquals(expected, actual);
    }

    @Test
    public void test_getCardPos_3Cards() {
        String expected = " <td>Your Future</td>";
        String actual = Card.getCardPosName(3,2);

        assertEquals(expected, actual);
    }

    @Test
    public void test_getCardPos_10Cards() {
        String expected = " <td>The Cards Advice</td>";
        String actual = Card.getCardPosName(10,6);

        assertEquals(expected, actual);
    }

    @Test
    public void test_getCardPos_invalidArg1() {
        assertThrows(IllegalArgumentException.class, () -> Card.getCardPosName(0, 3));
    }

    @Test
    public void test_getCardPos_invalidArg2() {
        assertThrows(IllegalArgumentException.class, () -> Card.getCardPosName(3, -1));
    }

    @Test
    public void test_getCardPos_2invalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> Card.getCardPosName(15, -1));
    }

}
