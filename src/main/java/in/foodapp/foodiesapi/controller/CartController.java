package in.foodapp.foodiesapi.controller;


import in.foodapp.foodiesapi.io.CartRequest;
import in.foodapp.foodiesapi.io.CartResponse;
import in.foodapp.foodiesapi.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {


    //======Add to cart==========
    private final CartService cartService;

    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request){

        String foodId = request.getFoodId();

        if (foodId == null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "foodId not found");
        }
       return cartService.addToCart(request);
    }

    @GetMapping
    public CartResponse getCart(){
     return cartService.getCart();

    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(){
        cartService.clearcart();

    }

    @PostMapping("/remove")
    public CartResponse removeFromCart(@RequestBody CartRequest request){
        String foodId = request.getFoodId();

        if (foodId == null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "foodId not found");
        }
        return cartService.removeFromCart(request);

    }
}
