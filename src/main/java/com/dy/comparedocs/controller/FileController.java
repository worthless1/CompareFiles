package com.dy.comparedocs.controller;

import com.dy.comparedocs.CompareDocs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${app.upload.homeDir:${user.home}}")
    public String homeDir;

    private final CompareDocs compareDocs;

    public FileController(CompareDocs compareDocs) {
        this.compareDocs = compareDocs;
    }


    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam MultipartFile file1, @RequestParam MultipartFile file2, RedirectAttributes redirectAttributes) {
        List<String> result = new ArrayList<>();

        String originalPath = (homeDir + File.separator + "1.txt");
        String modifiedPath = (homeDir + File.separator + "2.txt");
        File original = null, modified = null;
        //Checking for emptiness of files
        if (file1.isEmpty() || file2.isEmpty()) {
            logger.error("The file was not uploaded or was empty");
            result.add("Error: files were not uploaded or were empty.");
            result.add("Please check the files and try again.");
            redirectAttributes.addFlashAttribute("rows", result);
            return "redirect:/";
        }
        //Compare files
        try {
            //Uploading files to the server
            file1.transferTo(Paths.get(originalPath));
            file2.transferTo(Paths.get(modifiedPath));

            original = new File(originalPath);
            modified = new File(modifiedPath);
            result = compareDocs.comparison(original, modified);
        } catch (IOException e) {
            logger.error(e.getMessage());
            result.add("Error: " + e.getMessage());
        }
        redirectAttributes.addFlashAttribute("rows", result);
        return "redirect:/";
    }
}
