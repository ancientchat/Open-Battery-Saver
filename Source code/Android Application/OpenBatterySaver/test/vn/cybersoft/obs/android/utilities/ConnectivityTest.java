package vn.cybersoft.obs.android.utilities;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityTest {
    @Test
    public void testIsConnectedWifi_Connected_Wifi() {
        Context context = Mockito.mock(Context.class);
        ConnectivityManager cm = Mockito.mock(ConnectivityManager.class);
        NetworkInfo info = Mockito.mock(NetworkInfo.class);

        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cm);
        Mockito.when(cm.getActiveNetworkInfo()).thenReturn(info);
        Mockito.when(info.isConnected()).thenReturn(true);
        Mockito.when(info.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);

        assertTrue(Connectivity.isConnectedWifi(context));
    }

    @Test
    public void testIsConnectedWifi_Connected_Mobile() {
        Context context = Mockito.mock(Context.class);
        ConnectivityManager cm = Mockito.mock(ConnectivityManager.class);
        NetworkInfo info = Mockito.mock(NetworkInfo.class);

        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cm);
        Mockito.when(cm.getActiveNetworkInfo()).thenReturn(info);
        Mockito.when(info.isConnected()).thenReturn(true);
        Mockito.when(info.getType()).thenReturn(ConnectivityManager.TYPE_MOBILE);

        assertFalse(Connectivity.isConnectedWifi(context));
    }

    @Test
    public void testIsConnectedWifi_NotConnected() {
        Context context = Mockito.mock(Context.class);
        ConnectivityManager cm = Mockito.mock(ConnectivityManager.class);
        NetworkInfo info = Mockito.mock(NetworkInfo.class);

        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cm);
        Mockito.when(cm.getActiveNetworkInfo()).thenReturn(info);
        Mockito.when(info.isConnected()).thenReturn(false);
        Mockito.when(info.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);

        assertFalse(Connectivity.isConnectedWifi(context));
    }

    @Test
    public void testIsConnectedWifi_NullInfo() {
        Context context = Mockito.mock(Context.class);
        ConnectivityManager cm = Mockito.mock(ConnectivityManager.class);

        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cm);
        Mockito.when(cm.getActiveNetworkInfo()).thenReturn(null);

        assertFalse(Connectivity.isConnectedWifi(context));
    }
}
