package vn.cybersoft.obs.android.utilities;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogTest {

    @Test
    public void testFormatTime() {
        long millis = 1609459200000L; // 2021-01-01 00:00:00.000 GMT
        String formatted = Log.formatTime(millis);

        assertNotNull(formatted);

        // Ensure the string has some expected characters (like colon)
        assertEquals(true, formatted.contains(":"));

        // Exact match with local timezone
        String expected = new SimpleDateFormat("HH:mm:ss.SSS/E").format(new Date(millis));
        assertEquals(expected, formatted);
    }
}
