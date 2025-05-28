package com.rrecom.ecomsoft.service.imp;

import com.rrecom.ecomsoft.entity.CategoryEntity;
import com.rrecom.ecomsoft.io.CategoryRequest;
import com.rrecom.ecomsoft.io.CategoryResponse;
import com.rrecom.ecomsoft.repository.CategoryRepository;
import com.rrecom.ecomsoft.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.rrecom.ecomsoft.service.CategoryService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private  final CategoryRepository categoryRepository;
     private final ItemRepository itemRepository;

    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file){
    CategoryEntity newCategory = convertToEntity(request);
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

             categoryRepository.delete(existingCategory);
    }



















    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
      Integer itemCount=   itemRepository.countByCategoryId(newCategory.getId());
      return   CategoryResponse.builder().categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
              .imgUrl(newCategory.getImgUrl())
              .createdAt(newCategory.getCreatedAt())
              .updateAt(newCategory.getUpdatedAt())
              .items(itemCount)
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
