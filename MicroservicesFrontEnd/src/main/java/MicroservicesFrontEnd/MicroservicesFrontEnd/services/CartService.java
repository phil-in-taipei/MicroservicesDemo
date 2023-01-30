package MicroservicesFrontEnd.MicroservicesFrontEnd.services;
import MicroservicesFrontEnd.MicroservicesFrontEnd.models.Cart;
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

    public Cart fetchUsersCart(Long userId) {
        ResponseEntity<Cart> response = restTemplate.getForEntity
                ("http://CART-MICROSERVICE/cart/" + userId, Cart.class);
        return checkStatus(response);
    }

    public Cart processCartItems(Cart cart) {
        if (cart.getItems() != null) {
            for (Item cartItem : cart.getItems()) {
                Item item = itemService.fetchItemById(cartItem.getId());
                cartItem.setName(item.getName());
                cartItem.setDescription(item.getDescription());
            }
        } else {
            cart.setItems(new ArrayList<>());
        }
        return cart;
    }

    public Cart checkStatus(ResponseEntity<Cart> response) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return processCartItems(response.getBody());
        } else {
            return new Cart();
        }
    }
}
