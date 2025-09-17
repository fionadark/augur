import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;
import augur.Card;
import augur.Spread;
import org.junit.jupiter.api.Test;

public class SpreadTest {

    @Test
    public void test_getShortNames_returnsExpectedNames() {
        Card card1 = new Card("Card One", "C1", "value1", 1, "suit1", "type1", "up1", "rev1", "desc1");
        Card card2 = new Card("Card Two", "C2", "value2", 2, "suit2", "type2", "up2", "rev2", "desc2");

        Spread spread = new Spread(2, Arrays.asList(card1, card2));

        List<String> expected = Arrays.asList("C1", "C2");
        List<String> actual = spread.getShortNames();

        assertEquals(expected, actual);
    }

    @Test
    public void test_getNHits_returnsExpectedValue() {
        Spread spread = new Spread(3, Arrays.asList());
        assertEquals(3, spread.getNHits());
    }

    @Test
    public void test_getCards_returnsExpectedList() {
        Card card1 = new Card("Card One", "C1", "value1", 1, "suit1", "type1", "up", "rev", "img1");
        Card card2 = new Card("Card Two", "C2", "value2", 2, "suit2", "type2", "up", "rev", "img2");
        List<Card> expectedCards = Arrays.asList(card1, card2);

        Spread spread = new Spread(2, expectedCards);
        assertIterableEquals(expectedCards, spread.getCards());
    }

    // Note to self: Add tests for selectTarotSpread
    
}