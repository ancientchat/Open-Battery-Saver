package vn.cybersoft.obs.android.utilities;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    public static class MockR {
        public static final int test_field = 123;
        public static int non_final_field = 456;
        private static final int private_field = 789;
        public final int non_static_field = 101;
        public static final String string_field = "not an int";
    }

    @Test
    public void testGetResourceId_Success() {
        int result = ReflectionUtils.getResourceId("test_field", MockR.class);
        assertEquals(123, result);
    }

    @Test
    public void testGetResourceId_SuccessNonFinal() {
        int result = ReflectionUtils.getResourceId("non_final_field", MockR.class);
        assertEquals(456, result);
    }

    @Test
    public void testGetResourceId_NotFound() {
        int result = ReflectionUtils.getResourceId("non_existent", MockR.class);
        assertEquals(-1, result);
    }

    @Test
    public void testGetResourceId_PrivateField() {
        // getField only returns public fields, so this throws NoSuchFieldException
        int result = ReflectionUtils.getResourceId("private_field", MockR.class);
        assertEquals(-1, result);
    }

    @Test
    public void testGetResourceId_NonStaticField() {
        // idField.getInt(idField) will fail because idField is not an instance of MockR
        // and it's not static. This throws IllegalArgumentException.
        int result = ReflectionUtils.getResourceId("non_static_field", MockR.class);
        assertEquals(-1, result);
    }

    @Test
    public void testGetResourceId_WrongType() {
        // getInt throws IllegalArgumentException if field is not an int
        int result = ReflectionUtils.getResourceId("string_field", MockR.class);
        assertEquals(-1, result);
    }

    @Test
    public void testGetResourceId_NullClass() {
        int result = ReflectionUtils.getResourceId("test_field", null);
        assertEquals(-1, result);
    }

    @Test
    public void testGetResourceId_NullVariableName() {
        int result = ReflectionUtils.getResourceId(null, MockR.class);
        assertEquals(-1, result);
    }
}
