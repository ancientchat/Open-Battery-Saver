package vn.cybersoft.obs.android.utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class LogTest {

    private TimeZone originalTimeZone;
    private Locale originalLocale;

    @Before
    public void setUp() {
        originalTimeZone = TimeZone.getDefault();
        originalLocale = Locale.getDefault();

        // Set a fixed timezone and locale to ensure deterministic test results
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Locale.setDefault(Locale.US);
    }

    @After
    public void tearDown() {
        // Restore original defaults
        TimeZone.setDefault(originalTimeZone);
        Locale.setDefault(originalLocale);
    }

    @Test
    public void testFormatTime_epoch() {
        long millis = 0L; // 1970-01-01 00:00:00.000 UTC
        String formatted = Log.formatTime(millis);
        // Date(0) in UTC is Thursday, Jan 1 1970
        assertEquals("00:00:00.000/Thu", formatted);
    }

    @Test
    public void testFormatTime_knownTime() {
        // Example: 2023-10-15 12:30:45.123 UTC
        // Epoch: 1697373045123
        // 2023-10-15 is Sunday
        long millis = 1697373045123L;
        String formatted = Log.formatTime(millis);
        assertEquals("12:30:45.123/Sun", formatted);
    }
}
