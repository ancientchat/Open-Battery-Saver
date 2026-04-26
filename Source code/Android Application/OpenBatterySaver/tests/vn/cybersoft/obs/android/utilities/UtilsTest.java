package vn.cybersoft.obs.android.utilities;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import android.os.Build;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UtilsTest {

    private int originalSdkInt;

    /**
     * Standard reflection helper to set static fields for testing, similar to
     * Robolectric's ReflectionHelpers.setStaticField, allowing us to test
     * different Android SDK versions without pulling in the entire Robolectric
     * dependency tree which fails to resolve in this environment.
     */
    public static void setStaticField(Field field, Object value) throws Exception {
        field.setAccessible(true);
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, value);
        } catch (NoSuchFieldException e) {
            // JVMs >= 12 or Dalvik/ART might not have 'modifiers' field.
            // Attempt using Unsafe as fallback for older Android/JVMs.
            try {
                Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
                Field unsafeField;
                try {
                    unsafeField = unsafeClass.getDeclaredField("theUnsafe"); // Hotspot
                } catch (NoSuchFieldException ex) {
                    unsafeField = unsafeClass.getDeclaredField("THE_ONE"); // Dalvik/ART
                }
                unsafeField.setAccessible(true);
                Object unsafe = unsafeField.get(null);

                Object staticFieldBase = unsafeClass.getMethod("staticFieldBase", Field.class).invoke(unsafe, field);
                long staticFieldOffset = (Long) unsafeClass.getMethod("staticFieldOffset", Field.class).invoke(unsafe, field);

                unsafeClass.getMethod("putInt", Object.class, long.class, int.class).invoke(unsafe, staticFieldBase, staticFieldOffset, (Integer) value);
            } catch (Exception ex) {
                // If everything fails, throw to fail the test.
                throw new RuntimeException("Failed to modify static final field", ex);
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        originalSdkInt = Build.VERSION.SDK_INT;
    }

    @After
    public void tearDown() throws Exception {
        Field f = Build.VERSION.class.getField("SDK_INT");
        setStaticField(f, originalSdkInt);
    }

    @Test
    public void testIsKitKatOrLater_KitKat() throws Exception {
        Field f = Build.VERSION.class.getField("SDK_INT");
        setStaticField(f, Build.VERSION_CODES.KITKAT); // 19
        assertTrue("Expected isKitKatOrLater() to return true on KitKat (API 19)", Utils.isKitKatOrLater());
    }

    @Test
    public void testIsKitKatOrLater_JellyBeanMR2() throws Exception {
        Field f = Build.VERSION.class.getField("SDK_INT");
        setStaticField(f, Build.VERSION_CODES.JELLY_BEAN_MR2); // 18
        assertFalse("Expected isKitKatOrLater() to return false on Jelly Bean MR2 (API 18)", Utils.isKitKatOrLater());
    }

    @Test
    public void testIsKitKatOrLater_Lollipop() throws Exception {
        Field f = Build.VERSION.class.getField("SDK_INT");
        setStaticField(f, Build.VERSION_CODES.LOLLIPOP); // 21
        assertTrue("Expected isKitKatOrLater() to return true on Lollipop (API 21)", Utils.isKitKatOrLater());
    }
}
