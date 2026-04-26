package vn.cybersoft.obs.android.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ConnectivityTest {

    private Context mockContext;
    private ConnectivityManager mockConnectivityManager;
    private NetworkInfo mockNetworkInfo;

    @Before
    public void setUp() {
        mockContext = Mockito.mock(Context.class);
        mockConnectivityManager = Mockito.mock(ConnectivityManager.class);
        mockNetworkInfo = Mockito.mock(NetworkInfo.class);

        when(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE))
                .thenReturn(mockConnectivityManager);
    }

    @Test
    public void testIsConnected_WithNullNetworkInfo_ReturnsFalse() {
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(null);

        boolean result = Connectivity.isConnected(mockContext);

        assertFalse(result);
    }

    @Test
    public void testIsConnected_WithNetworkInfoNotConnected_ReturnsFalse() {
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.isConnected()).thenReturn(false);

        boolean result = Connectivity.isConnected(mockContext);

        assertFalse(result);
    }

    @Test
    public void testIsConnected_WithNetworkInfoConnected_ReturnsTrue() {
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.isConnected()).thenReturn(true);

        boolean result = Connectivity.isConnected(mockContext);

        assertTrue(result);
    }
}
