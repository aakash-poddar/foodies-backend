package in.foodapp.foodiesapi.service;

import in.foodapp.foodiesapi.io.FoodRequest;
import in.foodapp.foodiesapi.io.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FoodService {

    Map<String, String> uploadFile(MultipartFile file);

    FoodResponse addFood(FoodRequest request, MultipartFile file);

   List<FoodResponse> readFoods();

   FoodResponse readFood(String id);

    boolean deleteFile(String publicId);

    void deleteFood(String id);
}
