package in.foodapp.foodiesapi.service;

import com.razorpay.RazorpayException;
import in.foodapp.foodiesapi.io.OrderRequest;
import in.foodapp.foodiesapi.io.OrderResponse;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException;

   void varifyPayment(Map<String,String> paymentdata, String status);

   //LoggedIn user
   List<OrderResponse> getUserOrders();

   void removeOrder(String orderId);


   //Admin
   List<OrderResponse> getOrdersOfAllUsers();

   void updateOrderStatus(String orderId,String status);

}
