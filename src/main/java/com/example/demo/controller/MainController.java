package com.example.demo.controller;

import com.example.demo.model.Painting;
import com.example.demo.service.PaintingRepoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
public class MainController {
    @Value("${local_path_value}")
    private String local_path;

    Logger logger = LoggerFactory.getLogger(MainController.class);


    @Autowired
    private PaintingRepoImpl paintingRepo;


    @GetMapping("/home")
    public String home(Model model) {
        List<Painting> paintings = paintingRepo.selectAll();
        model.addAttribute("paintings",paintings);
        int i = 0;
        model.addAttribute("i",i);
        logger.info("works");
        return "gallery";
    }

    @GetMapping("/load")
    public String loadFile(Model model) {
        logger.info("works_load");
        return "load_painting";
    }


    @PostMapping("/save_painting")
    public String post(@RequestParam(name="file", required=false) MultipartFile file,
                       @RequestParam String title,
                       @RequestParam String size,
                       @RequestParam String year,
                       @RequestParam String description,
                       Model model) throws IOException {
        logger.info("post starts to work");
        try {
            //loading file works!!!
            String filename = null;
            if (file != null && !file.isEmpty()
                    && file.getContentType().equals("image/jpeg")) {
                filename = file.getOriginalFilename();
                FileOutputStream fos = new FileOutputStream(local_path+filename);
                byte[] buffer = file.getBytes();
                fos.write(buffer, 0, buffer.length);
                logger.info("write to file "+filename );
            }
            paintingRepo.addPainting(title, description, size, filename,year, true);
}
catch (Exception e) {
    model.addAttribute("message", e.getMessage());
}
        return "redirect:/load";
    }


}
