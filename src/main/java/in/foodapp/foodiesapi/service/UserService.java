package in.foodapp.foodiesapi.service;

import in.foodapp.foodiesapi.io.UserRequest;
import in.foodapp.foodiesapi.io.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    String findByUserId();
}
