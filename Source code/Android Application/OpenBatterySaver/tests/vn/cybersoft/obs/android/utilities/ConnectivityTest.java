package vn.cybersoft.obs.android.utilities;

import org.junit.Test;
import static org.junit.Assert.*;

import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

public class ConnectivityTest {

    @Test
    public void testIsConnectionFast_Wifi() {
        assertTrue("WIFI should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_WIFI, 0));
        assertTrue("WIFI should be fast regardless of subtype", Connectivity.isConnectionFast(ConnectivityManager.TYPE_WIFI, TelephonyManager.NETWORK_TYPE_EDGE));
    }

    @Test
    public void testIsConnectionFast_Mobile_Slow() {
        assertFalse("1xRTT should be slow", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_1xRTT));
        assertFalse("CDMA should be slow", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_CDMA));
        assertFalse("EDGE should be slow", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_EDGE));
        assertFalse("GPRS should be slow", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_GPRS));
        assertFalse("IDEN should be slow", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_IDEN));
        assertFalse("UNKNOWN should be slow", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_UNKNOWN));
    }

    @Test
    public void testIsConnectionFast_Mobile_Fast() {
        assertTrue("EVDO_0 should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_EVDO_0));
        assertTrue("EVDO_A should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_EVDO_A));
        assertTrue("HSDPA should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_HSDPA));
        assertTrue("HSPA should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_HSPA));
        assertTrue("HSUPA should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_HSUPA));
        assertTrue("UMTS should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_UMTS));
        assertTrue("EHRPD should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_EHRPD));
        assertTrue("EVDO_B should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_EVDO_B));
        assertTrue("HSPAP should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_HSPAP));
        assertTrue("LTE should be fast", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_LTE));
    }

    @Test
    public void testIsConnectionFast_Mobile_Unrecognized() {
        assertFalse("Unrecognized subtype should default to slow", Connectivity.isConnectionFast(ConnectivityManager.TYPE_MOBILE, 9999));
    }

    @Test
    public void testIsConnectionFast_UnknownType() {
        assertFalse("Unknown type should be slow", Connectivity.isConnectionFast(9999, 0));
    }
}
