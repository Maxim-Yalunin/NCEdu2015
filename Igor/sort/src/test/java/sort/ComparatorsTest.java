package sort;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by igoryan on 15.11.15.
 */
public class ComparatorsTest {
    @Test
    public void testInsensitiveComparator() {
        Comparators cmp = new Comparators('i');
        assertTrue(cmp.getCmp().compare("TEST", "test") == 0);
    }

    @Test
    public void testRegisterComparator() {
        Comparators cmp = new Comparators();
        assertFalse(cmp.getCmp().compare("TEST", "test") == 0);
    }
}
