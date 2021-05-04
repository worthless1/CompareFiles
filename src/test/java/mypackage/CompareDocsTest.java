package mypackage;

import com.github.difflib.text.DiffRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompareDocsTest {

    private CompareDocs cd;

    @BeforeEach
    public void init() {
        cd = new CompareDocs();
    }

    @Test
    public void comparison() throws IOException {
        File file1 = new File(getPath("1.txt"));
        File file2 = new File(getPath("2.txt"));
        List<DiffRow> result = new ArrayList<DiffRow>();
        result.add(new DiffRow(DiffRow.Tag.EQUAL,"Hello", "Hello"));
        result.add(new DiffRow(DiffRow.Tag.INSERT,"", "Privet"));
        result.add(new DiffRow(DiffRow.Tag.EQUAL,"There is something", "There is something"));
        result.add(new DiffRow(DiffRow.Tag.EQUAL,"Bye", "Bye"));
        result.add(new DiffRow(DiffRow.Tag.DELETE,"Poka", ""));
        result.add(new DiffRow(DiffRow.Tag.EQUAL,"zzzzzzzz", "zzzzzzzz"));
        result.add(new DiffRow(DiffRow.Tag.EQUAL,"xxxxxxxx", "xxxxxxxx"));
        result.add(new DiffRow(DiffRow.Tag.CHANGE,"cccccccc", "cccccc"));
        result.add(new DiffRow(DiffRow.Tag.CHANGE,"End of file", "End of file!"));
        assertEquals(result, cd.comparison(file1, file2));
    }

    @Test
    public void comparisonRussian() throws IOException {
        File file1 = new File(getPath("1russian.txt"));
        File file2 = new File(getPath("2russian.txt"));
        List<DiffRow> result = new ArrayList<DiffRow>();
        result.add(new DiffRow(DiffRow.Tag.EQUAL,"С 15 мая 1935 года в Москве работает метрополитен, являющийся основным средством передвижения в пределах столицы.",
                "С 15 мая 1935 года в Москве работает метрополитен, являющийся основным средством передвижения в пределах столицы."));
        result.add(new DiffRow(DiffRow.Tag.INSERT,"", "Эта строка добавлена"));
        result.add(new DiffRow(DiffRow.Tag.EQUAL,"В среднем московское метро перевозит 6,498 млн пассажиров в день.",
                "В среднем московское метро перевозит 6,498 млн пассажиров в день."));
        result.add(new DiffRow(DiffRow.Tag.CHANGE,"Это шестая по годовому пассажиропотоку система метро в мире и первая в Европе.",
                "Это шестая по годовому пассажиропотоку система метро в мире и первая в Европе!"));
        result.add(new DiffRow(DiffRow.Tag.CHANGE,"Общая протяжённость линий Московского метрополитена - 408,1 км, большая часть пути и станций находится под землёй.",
                "Общая протяжённость линий Московского метрополитена - 408,1 км, большая часть пути и станций находится под землёй!!!"));
        result.add(new DiffRow(DiffRow.Tag.EQUAL,"По протяжённости линий Московский метрополитен занимает четвёртое место в мире.",
                "По протяжённости линий Московский метрополитен занимает четвёртое место в мире."));
        result.add(new DiffRow(DiffRow.Tag.DELETE,"Эта строка удалена", ""));
        assertEquals(result, cd.comparison(file1, file2));
    }

    @Test
    public void testComparisonFileNotFound() {
        File path1 = new File("doesntExist");
        File path2 = new File("doesntExist1");
        Assertions.assertThrows(NoSuchFileException.class, ()-> cd.comparison(path1, path2));
    }

    @Test
    public void testComparisonIdenticalFileNames() throws IOException {

        File file1 = new File(getPath("1.txt"));
        File file2 = new File(getPath("1.txt"));
        Assertions.assertThrows(FileNotFoundException.class, ()-> cd.comparison(file1, file2));
    }

    public String getPath(String name) {
        String path = Objects.requireNonNull(getClass().getClassLoader().getResource(name)).getPath();
        return path;
    }
}