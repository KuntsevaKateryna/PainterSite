package com.example.demo.controller;

import com.example.demo.model.Painting;
import com.example.demo.repo.UserRepo;
import com.example.demo.security.UserDetailsServiceImpl;
import com.example.demo.service.PaintingRepoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {


    Logger logger = LoggerFactory.getLogger(MainController.class);


    @Autowired
    private PaintingRepoImpl paintingRepo;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;


    @Autowired
    private JavaMailSender javaMailSender;


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
if (!password.equals(password_double))
{
    model.addAttribute("message", "Passwords are not equal");
    return "registration";
}
else
{
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
  /*      SecurityContext securityContext = SecurityContextHolder.getContext();
        logger.info("securityContext.getAuthentication().getName() "+securityContext.getAuthentication().getName());
        logger.info("securityContext.getAuthentication().getAuthorities() "+securityContext.getAuthentication().getAuthorities());
        logger.info("securityContext.getAuthentication().getCredentials() "+securityContext.getAuthentication().getCredentials());
*/
        return "index";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
       //if (1 == 1) throw new RuntimeException("artificial exception");
        return "contact";
    }


    @GetMapping("/details/{id}") // id - dynamic param
    public String painterDetails(@PathVariable(value = "id") long id,
                                 Model model) {
        Painting painting = paintingRepo.getPainting(id);
        if (painting == null)
            return "redirect:/main/home";
        model.addAttribute("painting", painting);
        return "details";
    }
}
