package com.dy.comparedocs;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class CompareDocs {

    private static final Logger logger = LogManager.getLogger(CompareDocs.class);

    public List<String> comparison(File file1, File file2) throws IOException {
        logger.debug("Start reading files");
        List<String> original = Files.readAllLines(file1.toPath());
        List<String> revised = Files.readAllLines(file2.toPath());
        //File comparison
        DiffRowGenerator generator = DiffRowGenerator.create().build();
        List<DiffRow> rows = generator.generateDiffRows(original, revised);

        //Getting a list of changes in the desired form
        List<String> result = new ArrayList<>();
        int count = 0;
        for (DiffRow row : rows) {
            count++;
            switch (row.getTag()) {
                case CHANGE:
                    result.add(String.format("%s: CHANGE <%s> to <%s>", count, row.getOldLine(), row.getNewLine()));
                    break;
                case DELETE:
                    result.add(String.format("%s: DELETE <%s>", count, row.getOldLine()));
                    break;
                case INSERT:
                    result.add(String.format("%s: INSERT <%s>", count, row.getNewLine()));
                    break;
            }
        }
        //Adding a message that there are no differences in the texts
        if (result.isEmpty()) {
            result.add("There is no difference between the contents of the files.");
        }
        logger.debug("Successful comparison");

        return result;
    }
}
