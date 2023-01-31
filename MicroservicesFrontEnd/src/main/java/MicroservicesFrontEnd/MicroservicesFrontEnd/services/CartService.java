package MicroservicesFrontEnd.MicroservicesFrontEnd.services;
import MicroservicesFrontEnd.MicroservicesFrontEnd.models.Cart;
import MicroservicesFrontEnd.MicroservicesFrontEnd.models.CartItem;
import MicroservicesFrontEnd.MicroservicesFrontEnd.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class CartService {
    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    @Autowired
    ItemService itemService;


    public Cart addItemtoCart(Long userId, Long itemId) {
        System.out.println("******Adding item to cart*******************");
        System.out.println("User id: " + userId + "; Item id: " + itemId);
        ResponseEntity<Cart> response = restTemplate.postForEntity
                ("http://CART-MICROSERVICE/cart/" + userId + "?item-id=" + itemId, null, Cart.class);
        return checkStatus(response);
    }

    public Cart fetchUsersCart(Long userId) {
        ResponseEntity<Cart> response = restTemplate.getForEntity
                ("http://CART-MICROSERVICE/cart/" + userId, Cart.class);
        return checkStatus(response);
    }

    public Cart patchAmountOfItemsInCart(Long userId, Long cartItemId, Integer amount) {
        ResponseEntity<Cart> response = restTemplate.exchange
                ("http://CART-MICROSERVICE/cart/" + userId + "/" + cartItemId + "?amount=" + amount,
                        HttpMethod.PATCH, null, Cart.class);
        return checkStatus(response);
    }

    public Cart populateCartItemFields(Cart cart) {
        if (cart.getItems() != null) {
            for (CartItem cartItem : cart.getItems()) {
                System.out.println("******Calling Item service to populate field*******************");
                System.out.println("Cart id: " + cart.getUserId() + "; Item id: " + cartItem.getItemId());
                Item item = itemService.fetchItemById(cartItem.getItemId());
                cartItem.setName(item.getName());
                cartItem.setDescription(item.getDescription());
            }
        } else {
            cart.setItems(new ArrayList<>());
        }
        return cart;
    }

    public Cart deleteItemFromCart(Long userId, Long cartItemId) {
        ResponseEntity<Cart> response = restTemplate.exchange
                ("http://CART-MICROSERVICE/cart/" + userId + "/" + cartItemId,
                        HttpMethod.DELETE, null, Cart.class);
        return checkStatus(response);
    }

    public Cart checkStatus(ResponseEntity<Cart> response) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return populateCartItemFields(response.getBody());
        } else {
            return new Cart();
        }
    }
}
