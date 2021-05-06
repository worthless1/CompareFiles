package com.dy.comparedocs;

import com.github.difflib.text.DiffRow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FileComparatorApplication implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(FileComparatorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FileComparatorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        CompareDocs cd = new CompareDocs();
        List<DiffRow> rows = new ArrayList<DiffRow>();
        //checking the number of arguments entered
        if (args.length < 2) {
            System.err.println("Specify filenames in program arguments!");
            logger.error("File names were not specified in program arguments");
            System.exit(0);
        }
        //checking for the existence of files
        File file1 = new File(args[0]);
        File file2 = new File(args[1]);
        if (file1.exists() && file2.exists()) {
            try {
                rows = cd.comparison(file1, file2);
                cd.print(rows);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                logger.error(e);
            }
        } else {
            logger.error("File " + file1 + " or " + file2 + " was not found");
            System.err.printf("Check the spelling of the file name. %s or %s (The specified file cannot be found)", args[0], args[1]);
        }
    }

}