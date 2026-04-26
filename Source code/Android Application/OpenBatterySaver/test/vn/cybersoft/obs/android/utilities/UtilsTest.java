package vn.cybersoft.obs.android.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

@RunWith(RobolectricTestRunner.class)
public class UtilsTest {

    @Test
    public void testSaveToPreferenceWithUnhandledType() {
        Context context = RuntimeEnvironment.application;

        // Save initial state or clear preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().commit();

        // Pass an unhandled type, e.g., a custom Object
        Object unhandledTypeObject = new Object();

        boolean result = Utils.saveToPreference(context, "test_key", unhandledTypeObject);

        // Verify that the preference was not saved
        assertFalse(prefs.contains("test_key"));

        // commit should still return true since we just commit an empty transaction
        assertTrue(result);
    }
}
