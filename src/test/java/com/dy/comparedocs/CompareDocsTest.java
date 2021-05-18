package com.dy.comparedocs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CompareDocsTest {

    @Value("${app.upload.homeDir:${user.home}}")
    public String homeDir;

    @Autowired
    private CompareDocs compareDocs;

    @Test
    public void testComparison() throws IOException {
        File original = new File(getPath("1.txt"));
        File modified = new File(getPath("2.txt"));
        List<String> result = new ArrayList<>();
        result.add("2: INSERT <Privet>");
        result.add("5: DELETE <Poka>");
        result.add("8: CHANGE <cccccccc> to <cccccc>");
        result.add("9: CHANGE <End of file> to <End of file!>");
        assertEquals(result, compareDocs.comparison(original, modified));
    }

    @Test
    public void testComparisonRussian() throws IOException {
        File original = new File(getPath("1russian.txt"));
        File modified = new File(getPath("2russian.txt"));
        List<String> result = new ArrayList<>();
        result.add("2: INSERT <Эта строка добавлена>");
        result.add("4: CHANGE <Это шестая по годовому пассажиропотоку система метро в мире и первая в Европе.>" +
                " to <Это шестая по годовому пассажиропотоку система метро в мире и первая в Европе!>");
        result.add("5: CHANGE <Общая протяжённость линий Московского метрополитена - 408,1 км, большая часть пути и станций находится под землёй.>" +
                " to <Общая протяжённость линий Московского метрополитена - 408,1 км, большая часть пути и станций находится под землёй!!!>");
        result.add("7: DELETE <Эта строка удалена>");
        assertEquals(result, compareDocs.comparison(original, modified));
    }

    @Test
    public void testComparisonFileNotFound() {
        File original = new File("doesntExist");
        File modified = new File("doesntExist1");
        Assertions.assertThrows(NoSuchFileException.class, () -> compareDocs.comparison(original, modified));
    }

    @Test
    public void testComparisonIdenticalFiles() throws IOException {
        File original = new File(getPath("1.txt"));
        File modified = new File(getPath("1.txt"));
        List<String> result = new ArrayList<>();
        result.add("There is no difference between the contents of the files.");
        assertEquals(result, compareDocs.comparison(original, modified));
    }

    public String getPath(String name) {
        String path = Objects.requireNonNull(getClass().getClassLoader().getResource(name)).getPath();
        return path;
    }
}