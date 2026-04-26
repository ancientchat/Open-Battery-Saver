package vn.cybersoft.obs.android.utilities.test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import android.content.Context;
import android.net.wifi.WifiManager;

import vn.cybersoft.obs.android.utilities.DeviceUtils;
import vn.cybersoft.obs.android.utilities.Log;

public class DeviceUtilsTest {

    @Mock
    Context mockContext;

    @Mock
    WifiManager mockWifiManager;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockContext.getSystemService(Context.WIFI_SERVICE)).thenReturn(mockWifiManager);
    }

    @Test
    public void testTurnOnWifi_WhenWifiDisabledAndOnIsTrue() {
        try (MockedStatic<Log> mockedLog = mockStatic(Log.class)) {
            // WIFI_STATE_DISABLED is 1
            when(mockWifiManager.getWifiState()).thenReturn(1);

            DeviceUtils.turnOnWifi(mockContext, true);

            verify(mockWifiManager).setWifiEnabled(true);
        }
    }

    @Test
    public void testTurnOnWifi_WhenWifiEnabledAndOnIsFalse() {
        try (MockedStatic<Log> mockedLog = mockStatic(Log.class)) {
            // WIFI_STATE_ENABLED is 3
            when(mockWifiManager.getWifiState()).thenReturn(3);

            DeviceUtils.turnOnWifi(mockContext, false);

            verify(mockWifiManager).setWifiEnabled(false);
        }
    }

    @Test
    public void testTurnOnWifi_WhenWifiAlreadyEnabledAndOnIsTrue() {
        try (MockedStatic<Log> mockedLog = mockStatic(Log.class)) {
            // WIFI_STATE_ENABLED is 3
            when(mockWifiManager.getWifiState()).thenReturn(3);

            DeviceUtils.turnOnWifi(mockContext, true);

            verify(mockWifiManager, never()).setWifiEnabled(anyBoolean());
        }
    }

    @Test
    public void testTurnOnWifi_WhenWifiAlreadyDisabledAndOnIsFalse() {
        try (MockedStatic<Log> mockedLog = mockStatic(Log.class)) {
            // WIFI_STATE_DISABLED is 1
            when(mockWifiManager.getWifiState()).thenReturn(1);

            DeviceUtils.turnOnWifi(mockContext, false);

            verify(mockWifiManager, never()).setWifiEnabled(anyBoolean());
        }
    }
}
