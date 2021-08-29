package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repo.ReviewRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.security.SecurityUser;
import com.example.demo.security.UserDetailsServiceImpl;
import com.example.demo.service.PaintingRepoImpl;
import com.example.demo.service.ReviewRepoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/main")
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private PaintingRepoImpl paintingRepo;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ReviewRepoServiceImpl reviewRepoServiceImpl;

    @Autowired
    private JavaMailSender javaMailSender;

    //show all comments on painting
    public void showAllComments(Long paintingId, Model model){
        List<Review> allReviews =  reviewRepoServiceImpl.selectAllPaintingReviews(paintingId);
        model.addAttribute("allReviews", allReviews);
    }


    @GetMapping("/registration")
    public String registrationForm(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String save_new_user( @RequestParam String email,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String password,
                               @RequestParam String password_double,
                               Model model) {
        if (!password.equals(password_double)) {
            model.addAttribute("message", "Passwords are not equal");
            return "registration";
        }
        else {
            userDetailsServiceImpl.addUser( email, firstName, lastName, password);
        }
        return "registration";
        }

    @PostMapping("/contact")
    public String sendMessage( @RequestParam String First_Name,
                               @RequestParam String Last_Name,
                               @RequestParam String Email_Address,
                               @RequestParam String message,
                               Model model) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("k.kuntseva@gmail.com");
        msg.setSubject("From My Site");
        msg.setFrom(Email_Address);
        logger.info(msg.getFrom());
        msg.setText(message);
        javaMailSender.send(msg);
        return "contact";
    }


    @GetMapping("/home")
    public String home(Model model) {
        List<Painting> paintings = paintingRepo.selectAll();
        model.addAttribute("paintings", paintings);
        int i = 0;
        model.addAttribute("i", i);
        logger.info("works");
         return "index";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        return "contact";
    }


    @GetMapping("/details/{id}") // id - dynamic param
    public String painterDetails(@PathVariable(value = "id") long id,
                                 Model model) {
        Painting painting = paintingRepo.getPainting(id);
        if (painting == null)
            return "redirect:/main/home";
        model.addAttribute("painting", painting);
        //show all comments
        showAllComments(id, model);
        SecurityContext securityContext = SecurityContextHolder.getContext();
         if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().
                stream().filter(s -> s.toString().equals(Permision.WRITING.getPermission())).count()>0 ) {
             //if user with admin role is loaded
             return "details_with_Remove_Correct";
        }
         else
            return "details";
    }

    @PostMapping("/addComment/{id}")
    public String detailsAddComment(@PathVariable(value = "id") long id,
                                    @RequestParam String comment,
                                    Model model) {
       String username = userDetailsServiceImpl.currentUserNameSimple();
        String redirectPage = "details";
        try {
            Painting p = paintingRepo.getPainting(id);
                    model.addAttribute("painting", p);
            reviewRepoServiceImpl.addReview(username, comment, id);
            showAllComments(id, model);
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().
                    stream().filter(s -> s.toString().equals(Permision.WRITING.getPermission())).count()>0 ) {
                //if user with admin role is loaded
                redirectPage = "details_with_Remove_Correct";
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getClass().toString());
            model.addAttribute("message", e.getMessage());
            redirectPage = "errorPage";
        }
        return redirectPage;
    }
}
