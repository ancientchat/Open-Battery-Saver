package vn.cybersoft.obs.android.utilities;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import android.content.Intent;
import android.os.BatteryManager;

public class UtilsTest {
    @Test
    public void testGetBatteryPercentageNormal() {
        Intent mockIntent = mock(Intent.class);
        when(mockIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)).thenReturn(50);
        when(mockIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)).thenReturn(100);

        assertEquals("50%", Utils.getBatteryPercentage(mockIntent));
    }

    @Test
    public void testGetBatteryPercentageZeroScale() {
        Intent mockIntent = mock(Intent.class);
        when(mockIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)).thenReturn(50);
        when(mockIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)).thenReturn(0);

        assertEquals("0%", Utils.getBatteryPercentage(mockIntent));
    }
}
