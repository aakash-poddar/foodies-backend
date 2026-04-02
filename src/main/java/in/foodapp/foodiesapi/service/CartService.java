package in.foodapp.foodiesapi.service;

import in.foodapp.foodiesapi.io.CartRequest;
import in.foodapp.foodiesapi.io.CartResponse;

public interface CartService {

     CartResponse addToCart(CartRequest request);

     CartResponse getCart();

     void clearcart();

    CartResponse removeFromCart(CartRequest cartRequest);

}
