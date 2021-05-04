package mypackage;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CompareDocs {

    private static final Logger logger = LogManager.getLogger(CompareDocs.class);

    public List<DiffRow> comparison(File file1, File file2) throws IOException {
        List<DiffRow> rows = null;
        if (file1.equals(file2)) {
            logger.error("The same file names were specified " + file1 + " and " + file2);
            throw new FileNotFoundException("Comparison error: to compare files, you need to specify different file names.");
        }
        logger.debug("Start reading files");
        List<String> original = Files.readAllLines(file1.toPath());
        List<String> revised = Files.readAllLines(file2.toPath());

        DiffRowGenerator generator = DiffRowGenerator.create().build();
        rows = generator.generateDiffRows(original, revised);
        return rows;
    }

    public void print(List<DiffRow> rows) {
        //outputting modified lines
        int count = 0;
        for (DiffRow row : rows) {
            count++;
            switch (row.getTag()) {
                case CHANGE:
                    System.out.printf("%s: CHANGE <%s> to <%s>\n", count, row.getOldLine(), row.getNewLine());
                    break;
                case DELETE:
                    System.out.printf("%s: DELETE <%s>\n", count, row.getOldLine());
                    break;
                case INSERT:
                    System.out.printf("%s: INSERT <%s>\n", count, row.getNewLine());
                    break;
            }
        }
        logger.debug("Lines printed successfully");
    }

}