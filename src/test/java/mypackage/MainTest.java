package mypackage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final Main mn = new Main();
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void main() {
        String path1 = classLoader.getResource("1.txt").getPath();
        String path2 = classLoader.getResource("2.txt").getPath();

    }
}