package mypackage;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompareDocsTest {

    private final CompareDocs cd = new CompareDocs();
    ClassLoader classLoader = getClass().getClassLoader();
    String path1 = classLoader.getResource("1.txt").getPath();
    String path2 = classLoader.getResource("2.txt").getPath();

    @Test
    public void testComparison() {
        cd.comparison(path1, path2);
        TreeMap<Integer, String> result = new TreeMap<>();
        result.put(2, "There is something!");
        result.put(3, "Bye!");
        assertEquals(result, cd.comparison(path1, path2));
    }

}