package mypackage;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CompareDocs {

    private static final Logger logger = LogManager.getLogger(CompareDocs.class);

    // Helper method for get the file content
    private static List fileToLines(String filename) {
        List lines = new LinkedList();
        String line = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            logger.error(e);
        }
        return lines;
    }

    public TreeMap<Integer, String> comparison(String path1, String path2) {
        TreeMap<Integer, String> differences = new TreeMap<>();
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
        }
        return differences;
    }

    public void print(TreeMap<Integer, String> diff) {
        //outputting modified lines
        for (Map.Entry<Integer, String> entry : diff.entrySet()) {
            System.out.printf("%d:<%s>\n", entry.getKey(), entry.getValue());

        }
        logger.debug("Lines printed successfully");

    }

    public void newComparison() throws IOException {
        List<String> original = Files.readAllLines(new File("1.txt").toPath());
        List<String> revised = Files.readAllLines(new File("2.txt").toPath());

        Patch<String> patch = DiffUtils.diff(original, revised);

        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(false)
                .mergeOriginalRevised(false)
                .build();

        List<DiffRow> rows = generator.generateDiffRows(original, revised);

        System.out.println("Первый сравниватель:");
        for (AbstractDelta delta : patch.getDeltas()) {
            System.out.println(delta.getType() + " " + delta.getTarget());
            System.out.println(delta);
        }
        int count = 0;
        System.out.println("\nВторой сравниватель:");
        for (DiffRow row : rows) {
            count++;
            if (!row.getTag().equals(DiffRow.Tag.EQUAL)) {
                //System.out.println(count + ": <" + row.getTag() + " " + row.getOldLine() + "> to <" + row.getNewLine() + ">");
                System.out.printf("%s: %s <%s> to <%s>\n", count, row.getTag(), row.getOldLine(), row.getNewLine());
            }
        }


    }


}