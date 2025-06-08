package com.rrecom.ecomsoft.service.imp;

import com.rrecom.ecomsoft.entity.CategoryEntity;
import com.rrecom.ecomsoft.io.CategoryRequest;
import com.rrecom.ecomsoft.io.CategoryResponse;
import com.rrecom.ecomsoft.repository.CategoryRepository;
import com.rrecom.ecomsoft.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.rrecom.ecomsoft.service.CategoryService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private  final CategoryRepository categoryRepository;
     private final ItemRepository itemRepository;

    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file)  throws Exception
    {
        String fileName=  UUID.randomUUID().toString()+"."+ StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path uploadPath =  Paths.get("upload").toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
        String imgUrl = "http://localhost:9091/api/v1.0/upload/" + fileName;




        CategoryEntity newCategory = convertToEntity(request);
    newCategory.setImgUrl(imgUrl);
            newCategory= categoryRepository.save(newCategory);
       return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> read() {
      return categoryRepository.findAll().stream().map(categoryEntity -> convertToResponse(categoryEntity))
               .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId) {


     CategoryEntity existingCategory =   categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(()->new RuntimeException(("Category not found:"+categoryId)));
 String imgUrl = existingCategory.getImgUrl();
            String fileName= imgUrl.substring(imgUrl.lastIndexOf("/")+1);
          Path uploadPath  =Paths.get("upload").toAbsolutePath().normalize();
         Path filePath= uploadPath.resolve(fileName);
         try {
             Files.deleteIfExists(filePath);
         }
         catch (IOException e)
         {
             e.printStackTrace();
         }



             categoryRepository.delete(existingCategory);
    }






    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
      return CategoryResponse.builder()
              .categoryId(newCategory.getCategoryId())
              .name(newCategory.getName())
              .description(newCategory.getDescription())
              .bgColor(newCategory.getBgColor())
              .imgUrl(newCategory.getImgUrl())
              .createdAt(newCategory.getCreatedAt())
              .updateAt(newCategory.getUpdatedAt())
              .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {

     return    CategoryEntity.builder().categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }


}
