import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    // Test selectTarotSpread


    // Test selectOneCardSpread
    @Test
    public void test_selectOneCardSpread_validInput() {
        Scanner mockScanner = new Scanner("1\n");
        boolean result = Spread.selectOneCardSpread(mockScanner);
        assertEquals(true, result);
    }

    @Test
    public void test_selectOneCardSpread_invalidInput() {
        Scanner mockScanner = new Scanner("invalid\n1\n");
        boolean result = Spread.selectOneCardSpread(mockScanner);
        assertEquals(true, result);
    }

    // Test selectThreeCardSpread
    @Test
    public void test_selectThreeCardSpread_validInput() {
        Scanner mockScanner = new Scanner("1\n");
        boolean result = Spread.selectThreeCardSpread(mockScanner);
        assertEquals(true, result);
    }

    @Test
    public void test_selectThreeCardSpread_invalidInput() {
        Scanner mockScanner = new Scanner("invalid\n1\n");
        boolean result = Spread.selectThreeCardSpread(mockScanner);
        assertEquals(true, result);
    }

    // Test selectTenCardSpread
    @Test
    public void test_selectTenCardSpread_validInput() {
        Scanner mockScanner = new Scanner("1\n");
        boolean result = Spread.selectTenCardSpread(mockScanner);
        assertEquals(true, result);
    }

    @Test
    public void test_selectTenCardSpread_invalidInput() {
        Scanner mockScanner = new Scanner("invalid\n1\n");
        boolean result = Spread.selectTenCardSpread(mockScanner);
        assertEquals(true, result);
    }

}