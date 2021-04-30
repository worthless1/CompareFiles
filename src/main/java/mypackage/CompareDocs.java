package mypackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class CompareDocs {

    private static final Logger logger = LogManager.getLogger(CompareDocs.class);

    public TreeMap<Integer, String> comparison(String path1, String path2) throws IOException {
        TreeMap<Integer, String> differences = new TreeMap<>();
        if (path1.equals(path2)) {
            System.err.println("To compare files, you need to specify different file names.");
            logger.error("The same file names were specified " + path1 + " and " + path2);
            return differences;

        }
        logger.debug("Start reading files");

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
            logger.info("Files were read successfully, number of lines: " + count);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            logger.error(e);
            throw e;
        }
        return differences;
    }

    public void print(TreeMap<Integer, String> diff) {
        if (diff.size() > 0){
            //outputting modified lines
            for (Map.Entry<Integer, String> entry : diff.entrySet()) {
                System.out.printf("%d:<%s>\n", entry.getKey(), entry.getValue());

            }
            logger.debug("Lines printed successfully");
        }

    }

}