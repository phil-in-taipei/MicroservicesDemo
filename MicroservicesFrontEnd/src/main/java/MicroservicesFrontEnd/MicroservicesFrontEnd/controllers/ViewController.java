package MicroservicesFrontEnd.MicroservicesFrontEnd.controllers;

import MicroservicesFrontEnd.MicroservicesFrontEnd.models.UserModel;
import MicroservicesFrontEnd.MicroservicesFrontEnd.services.ItemService;
import MicroservicesFrontEnd.MicroservicesFrontEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ViewController {
    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @GetMapping("/items")
    public String displayItems(Model model, Principal principal) {
        UserModel user = userService.getUser(principal.getName());
        model.addAttribute("userId", user.getId());
        model.addAttribute("items", itemService.getAllItems());
        return "item-list";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
