package mypackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CompareDocs {

    private static final Logger logger = LogManager.getLogger(CompareDocs.class);

    public void comparison(String path1, String path2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(path1));
             BufferedReader reader2 = new BufferedReader(new FileReader(path2))) {
            String line1 = null;
            String line2 = null;
            int number = 0;
            //reading lines from a stream
            while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
                number++;
                //outputting modified lines
                if (!line1.equals(line2)) {
                    System.out.printf("%d:<%s>%n", number, line2);
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            logger.error(e);
        }

    }

}