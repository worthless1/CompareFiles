package mypackage;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompareDocsTest {

    private CompareDocs cd;

    @BeforeEach
    public void init() {
        cd = new CompareDocs();
    }

    @Test
    public void testComparison() throws IOException {
        String path1 = getPath("1.txt");
        String path2 = getPath("2.txt");

        TreeMap<Integer, String> result = new TreeMap<>();
        result.put(2, "There is something!");
        result.put(3, "Bye!");
        assertEquals(result, cd.comparison(path1, path2));
    }

    @Test
    public void testComparisonRussian() throws IOException {
        String path1 = getPath("1russian.txt");
        String path2 = getPath("2russian.txt");

        TreeMap<Integer, String> result = new TreeMap<>();
        result.put(3, "Это шестая по годовому пассажиропотоку система метро в мире и первая в Европе!");
        result.put(4, "Общая протяжённость линий Московского метрополитена — 408,1 км, большая часть пути и станций находится под землёй!");
        result.put(5, "По протяжённости линий Московский метрополитен занимает четвёртое место в мире!");
        assertEquals(result, cd.comparison(path1, path2));
    }

    @Test
    public void testComparisonFileNotFound() {
        String path1 = ("doesntExist");
        String path2 = ("doesntExist1");
        Assertions.assertThrows(FileNotFoundException.class, ()-> cd.comparison(path1, path2));
    }


    @Test
    public void testComparisonIdenticalFileNames() throws IOException {
        String path1 = getPath("1.txt");
        String path2 = getPath("1.txt");
        TreeMap<Integer, String> result = new TreeMap<>();
        assertEquals(result, cd.comparison(path1, path2));
    }
    public String getPath(String name) {
        String path = Objects.requireNonNull(getClass().getClassLoader().getResource(name)).getPath();
        return path;
    }

}