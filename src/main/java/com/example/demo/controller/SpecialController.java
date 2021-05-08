package com.example.demo.controller;

import com.example.demo.model.Painting;
import com.example.demo.service.PaintingRepoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/special")
public class SpecialController {

    @Value("${local_path_value}")
    private String local_path;

    Logger logger = LoggerFactory.getLogger(SpecialController.class);


    @Autowired
    private PaintingRepoImpl paintingRepo;


    @Autowired
    private JavaMailSender javaMailSender;


    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/load")
    public String loadFile(Model model) {
        logger.info("works_load");
        return "load_painting";
    }



    public String load_file(MultipartFile file) throws IOException {
        //loading file works!!!
        String filename = null;
        if (file != null && !file.isEmpty()
                && file.getContentType().equals("image/jpeg")) {
            filename = file.getOriginalFilename();
            FileOutputStream fos = new FileOutputStream(local_path + filename);
            byte[] buffer = file.getBytes();
            fos.write(buffer, 0, buffer.length);
            logger.info("write to file " + filename);
        }
        return filename;
    }

    @PostMapping("/save_painting")
    public String post(@RequestParam(name = "file", required = false) MultipartFile file,
                       @RequestParam String title,
                       @RequestParam String size,
                       @RequestParam String year,
                       @RequestParam String description,
                       Model model) throws IOException {
        logger.info("post starts to work");
        try {
            String filename = load_file(file);
            paintingRepo.addPainting(title, description, size, filename, year, true);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "redirect:/special/load";
    }




    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/details/{id}/edit") // id - dynamic param
    public String detailsEdit(@PathVariable(value = "id") long id,
                              Model model) {
        Painting painting = paintingRepo.getPainting(id);
        if (painting == null)
            return "redirect:/main/home";
        model.addAttribute("painting", painting);
        return "details_edit";
    }

    @PostMapping("/details/{id}/edit")
    public String detailsUpdateblog(@RequestParam(name = "file", required = false) MultipartFile file,
                                    @PathVariable(value = "id") long id,
                                    @RequestParam String title,
                                    @RequestParam String description,
                                    @RequestParam String size,
                                    @RequestParam String year,
                                    @RequestParam Boolean in_stock,
                                    Model model) {

        Painting painting = null;
        String redirectPage = "redirect:/main/home";
        try {
            String filename = load_file(file);
            if (filename == null)
                filename = paintingRepo.getPainting(id).getPath();
            paintingRepo.correctPainting(id, title, description, size, filename, year, in_stock);

        } catch (Exception e) {
            model.addAttribute("error", e.getClass().toString());
            model.addAttribute("message", e.getMessage());
            redirectPage = "errorPage";
        }
        return redirectPage;
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/details/{id}/delete")
    public String deletePainting(@PathVariable(value = "id") long id,
                                 Model model) {

        String redirectPage = "redirect:/main/home";
        try {

            paintingRepo.deletePainting(id);

        } catch (Exception e) {
            model.addAttribute("error", e.getClass().toString());
            model.addAttribute("message", e.getMessage());
            redirectPage = "errorPage";
        }
        return redirectPage;
    }
}
