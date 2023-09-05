package com.project.bankcompany.controller;

import com.project.bankcompany.dao.RoleDao;
import com.project.bankcompany.dto.UserDto;
import com.project.bankcompany.entity.User;
import com.project.bankcompany.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") User user, Model model) {

        if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {

            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        } else {
            UserDto userDto = new UserDto();
            userService.save(userDto);

            return "redirect:/";
        }
    }

//    @RequestMapping("/userFront")
//    public String userFront(Principal principal, Model model) {
//        User user = userService.findByUsername(principal.getName());
//        PrimaryAccount primaryAccount = user.getPrimaryAccount();
//        SavingsAccount savingsAccount = user.getSavingsAccount();
//
//        model.addAttribute("primaryAccount", primaryAccount);
//        model.addAttribute("savingsAccount", savingsAccount);
//
//        return "userFront";
//    }
}

