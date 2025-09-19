import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;

import augur.Spread;
import augur.Tarot;
import org.junit.jupiter.api.Test;

public class TarotTest {

    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
    }

    // Test beginNewReading
    @Test
    public void test_beginNewReading_yesInput() {
        System.setIn(new ByteArrayInputStream("Y\n".getBytes()));
        Tarot tarot = new Tarot();

        tarot.beginNewReading();
        System.setIn(originalIn);
    }

    @Test
    public void test_beginNewReading_noInput() {
        System.setIn(new ByteArrayInputStream("N\n".getBytes()));
        Tarot tarot = new Tarot();

        tarot.beginNewReading();
        System.setIn(originalIn);
    }

    @Test
    public void test_beginNewReading_invalidInput() {
        System.setIn(new ByteArrayInputStream("maybe\n".getBytes()));
        Tarot.SCANNER = new Scanner(System.in); // Re-initialize to use test input
        Tarot tarot = new Tarot();

        assertThrows(IllegalArgumentException.class, tarot::beginNewReading);
        System.setIn(originalIn);
    }

    // Test reverseTrueOrFalse
    @Test
    public void test_reverseTrueOrFalse_randomness() {
        boolean foundTrue = false;
        boolean foundFalse = false;

        // Run the method 100 times to check both outcomes
        for (int i = 0; i < 100; i++) {
            boolean result = Tarot.reverseTrueOrFalse();
            if (result)
                foundTrue = true;
            else
                foundFalse = true;
        }

        assertTrue(foundTrue, "Should return true at least once");
        assertTrue(foundFalse, "Should return false at least once");
    }

    // Test tellFuture
    @Test
    public void test_tellFuture_invalidInput() {
        Tarot tarot = new Tarot();
        Spread spread = new Spread();
        assertThrows(IllegalArgumentException.class, () -> tarot.tellFuture(spread, "invalid"));
    }

    // Test call

}
