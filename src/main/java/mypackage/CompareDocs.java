package mypackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class CompareDocs {

    private static final Logger logger = LogManager.getLogger(CompareDocs.class);

    public TreeMap<Integer, String> comparison(String path1, String path2) {
        TreeMap<Integer, String> differences = new TreeMap<>();

        try (BufferedReader reader1 = new BufferedReader(new FileReader(path1));
             BufferedReader reader2 = new BufferedReader(new FileReader(path2))) {

            String line1 = null;
            String line2 = null;
            int count = 0;
            //reading lines from a stream
            while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
                count++;
                //adding lines to HashMap
                if (!line1.equals(line2)) {
                    differences.put(count, line2);
                }
            }
            logger.debug("files were read successfully, number of lines: " + count);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            logger.error(e);
        }
        return differences;
    }

    public void print(TreeMap<Integer, String> diff) {
        //outputting modified lines
        for (Map.Entry<Integer, String> entry : diff.entrySet()) {
            System.out.printf("%d:<%s>\n", entry.getKey(), entry.getValue());

        }
    }

}