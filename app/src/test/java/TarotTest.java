import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import augur.FileUtils;
import augur.Spread;
import augur.Tarot;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

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
    @Test
    public void test_call_simulateNetworkFailure() {
        System.setIn(new ByteArrayInputStream("1\n1\n".getBytes()));
        Scanner testScanner = new Scanner(System.in);

        try (MockedStatic<Spread> spreadMock = mockStatic(Spread.class)) {
            // Mock spread selection to return invalid URL
            spreadMock.when(() -> Spread.selectTarotSpread(any(Scanner.class), anyString()))
                    .thenReturn("https://invalid-url-that-does-not-exist.com/api?n=1");

            // Create a spy of Tarot to mock beginNewReading
            Tarot tarot = spy(new Tarot());
            doNothing().when(tarot).beginNewReading(); // Mock beginNewReading to do nothing

            // Temporarily replace the static SCANNER
            Scanner originalScanner = Tarot.SCANNER;
            Tarot.SCANNER = testScanner;

            try {
                String result = tarot.call();
                assertEquals("", result);
            } finally {
                // Restore original scanner
                Tarot.SCANNER = originalScanner;
            }
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void test_call_validInput() {
        System.setIn(new ByteArrayInputStream("1\n1\nN\n".getBytes()));
        Tarot.SCANNER = new Scanner(System.in);

        // Mock static methods to avoid external dependencies
        try (MockedStatic<Spread> spreadMock = mockStatic(Spread.class);
                MockedStatic<FileUtils> fileUtilsMock = mockStatic(FileUtils.class);
                MockedStatic<Desktop> desktopMock = mockStatic(Desktop.class)) {

            // Mock spread selection to return valid URL
            spreadMock.when(() -> Spread.selectTarotSpread(any(Scanner.class), anyString()))
                    .thenReturn("https://tarotapi.dev/api/v1/cards/random?n=1");

            // Mock file operations
            fileUtilsMock.when(() -> FileUtils.writeToFile(any(), anyString(), any())).thenAnswer(invocation -> null);

            // Mock desktop operations
            Desktop mockDesktop = mock(Desktop.class);
            desktopMock.when(Desktop::getDesktop).thenReturn(mockDesktop);

            Tarot tarot = new Tarot();
            String result = tarot.call();

            assertEquals("", result);
        }
        System.setIn(originalIn);
    }

}
