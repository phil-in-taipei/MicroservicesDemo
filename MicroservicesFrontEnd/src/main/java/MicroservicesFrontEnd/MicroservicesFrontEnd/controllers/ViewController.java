package MicroservicesFrontEnd.MicroservicesFrontEnd.controllers;

import MicroservicesFrontEnd.MicroservicesFrontEnd.models.UserModel;
import MicroservicesFrontEnd.MicroservicesFrontEnd.services.ItemService;
import MicroservicesFrontEnd.MicroservicesFrontEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.security.Principal;

@Controller
public class ViewController {
    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String showLandingPage() {
        return "index";
    }

    @GetMapping("/items")
    public String displayItems(Model model, Principal principal) {
        UserModel user = userService.getUser(principal.getName());
        model.addAttribute("userId", user.getId());
        model.addAttribute("items", itemService.fetchAllItems());
        return "item-list";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "authentication/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "authentication/register";
    }

    @PostMapping("/register")
    public String submitRegisterForm(@ModelAttribute("user") UserModel user, Model model) {
        UserModel createdUser = userService.registerNewUser(user);
        if (createdUser == null) {
            return "authentication/registration-failure";
        }
        model.addAttribute("user", createdUser);
        return "authentication/registration-success";
    }
}
