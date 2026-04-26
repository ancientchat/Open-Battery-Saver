package vn.cybersoft.obs.android.utilities;

import org.junit.Test;
import static org.junit.Assert.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

public class UtilsTest {

    @Test
    public void testSaveToPreferenceErrorPath() {
        Context mockContext = mock(Context.class);
        SharedPreferences mockPrefs = mock(SharedPreferences.class);
        SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);

        when(mockPrefs.edit()).thenReturn(mockEditor);
        when(mockEditor.commit()).thenReturn(true);

        try (MockedStatic<PreferenceManager> mockedPreferenceManager = mockStatic(PreferenceManager.class)) {
            mockedPreferenceManager.when(() -> PreferenceManager.getDefaultSharedPreferences(mockContext)).thenReturn(mockPrefs);

            // This is a custom object that is not a String, Integer, Float, Long, or Boolean
            Object customObject = new Object();

            boolean result = Utils.saveToPreference(mockContext, "testKey", customObject);

            // Verify that no put methods were called
            verify(mockEditor, never()).putString(anyString(), anyString());
            verify(mockEditor, never()).putInt(anyString(), anyInt());
            verify(mockEditor, never()).putFloat(anyString(), anyFloat());
            verify(mockEditor, never()).putLong(anyString(), anyLong());
            verify(mockEditor, never()).putBoolean(anyString(), anyBoolean());

            // Verify that commit was called
            verify(mockEditor).commit();

            assertTrue(result);
        }
    }
}
