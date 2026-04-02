package in.foodapp.foodiesapi.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import in.foodapp.foodiesapi.entity.FoodEntity;
import in.foodapp.foodiesapi.io.FoodRequest;
import in.foodapp.foodiesapi.io.FoodResponse;
import in.foodapp.foodiesapi.repository.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoodServiceImp implements  FoodService{

    private final Cloudinary cloudinary;
    private final FoodRepository foodRepository;


    //=======upload File to cloudinary method=======
    @Override
    public Map<String, String> uploadFile(MultipartFile file) {
        String publicId = "foodies/" + UUID.randomUUID();

        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", publicId
                    )
            );

            Map<String, String> result = new java.util.HashMap<>();
            result.put("url", uploadResult.get("secure_url").toString());
            result.put("publicId", uploadResult.get("public_id").toString());

            return result;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    //=========Add Food method=======
    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) {

        FoodEntity food = convertToEntity(request);
        Map<String, String> uploadResult = uploadFile(file);

        food.setImageUrl(uploadResult.get("url"));
        food.setPublicId(uploadResult.get("publicId"));
        food = foodRepository.save(food);

        return convertToResponse(food);
    }

    //=======Read all Foods method=======
    @Override
    public List<FoodResponse> readFoods() {
       List<FoodEntity> databaseEntries=  foodRepository.findAll();
       return databaseEntries.stream().map(object -> convertToResponse(object)).collect(Collectors.toList());
    }

    //=========Read one food method======
    @Override
    public FoodResponse readFood(String id) {
       FoodEntity existingFood =  foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found for the id: " + id));
       return convertToResponse(existingFood);
    }

    //=========Delete File Method from Cloudinary==========
    @Override
    public boolean deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
//========Delete Food Method ==========
    @Override
    public void deleteFood(String id) {

        FoodEntity food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        // Cloudinary delete (best-effort)
        deleteFile(food.getPublicId());

        //Check public id
        //System.out.println("PublicId from DB = " + food.getPublicId());


        // DB delete ALWAYS
        foodRepository.deleteById(id);

    }


    private FoodEntity convertToEntity(FoodRequest request){
       return FoodEntity.builder()
           .name(request.getName())
           .description(request.getDescription())
           .category(request.getCategory())
           .price(request.getPrice())
           .build();
    }

    private FoodResponse convertToResponse(FoodEntity entity){

         return  FoodResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .build();

    }
}
