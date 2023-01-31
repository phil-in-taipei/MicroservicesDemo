package MicroservicesFrontEnd.MicroservicesFrontEnd.controllers;


import MicroservicesFrontEnd.MicroservicesFrontEnd.models.Cart;
import MicroservicesFrontEnd.MicroservicesFrontEnd.models.UserModel;
import MicroservicesFrontEnd.MicroservicesFrontEnd.services.CartService;
import MicroservicesFrontEnd.MicroservicesFrontEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CartController {
    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;


    @GetMapping("/cart")
    public String displayCart(Model model, Principal principal) {
        UserModel user = userService.getUser(principal.getName());
        Cart cart = cartService.fetchUsersCart(user.getId());
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("userId", user.getId());
        return "users-cart";
    }

    @PostMapping("/cart/{userId}/{itemId}")
    public String postItemToCart(
            @PathVariable("userId") Long userId,
            @PathVariable("itemId") Long itemId, Model model) {
        Cart cart = cartService.addItemtoCart(userId, itemId);
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("userId", userId);
        return "users-cart";
    }

    @GetMapping("/item/{userId}/{cartItemId}/{amount}")
    public String modifyAmountOfItemsInCart(
            @PathVariable("userId") Long userId,
            @PathVariable("cartItemId") Long cartItemId,
            @PathVariable("amount") Integer amount, Model model) {
        Cart cart;
        if (amount == 0) {
            System.out.println("No items are in the cart!");
            cart = cartService.fetchUsersCart(userId);
            //cart = cartService.removeCartItem(userId, cartItemId);
        } else {
            cart = cartService.patchAmountOfItemsInCart(userId, cartItemId, amount);
        }
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("userId", userId);
        return "users-cart";
    }

    @GetMapping("/cart/{userId}/{cartItemId}")
    public String removeItemFromCart(@PathVariable("userId") Long userId,
                                 @PathVariable("cartItemId") Long cartItemId, Model model) {
        Cart cart = cartService.deleteItemFromCart(userId, cartItemId);
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("userId", userId);
        return "users-cart";
    }
}
