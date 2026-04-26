package vn.cybersoft.obs.android.utilities;
import static org.junit.Assert.*;
import org.junit.Test;
import android.os.Build;
public class UtilsTest {
    @Test
    public void testIsIceCreamSandwichOrLater_Before() {
        Build.VERSION.SDK_INT = 13;
        assertFalse("Should be false for SDK 13", Utils.isIceCreamSandwichOrLater());
    }
    @Test
    public void testIsIceCreamSandwichOrLater_Equal() {
        Build.VERSION.SDK_INT = 14;
        assertTrue("Should be true for SDK 14", Utils.isIceCreamSandwichOrLater());
    }
    @Test
    public void testIsIceCreamSandwichOrLater_After() {
        Build.VERSION.SDK_INT = 15;
        assertTrue("Should be true for SDK 15", Utils.isIceCreamSandwichOrLater());
    }
}
