package com.rrecom.ecomsoft.service.imp;


import com.rrecom.ecomsoft.entity.CategoryEntity;
import com.rrecom.ecomsoft.entity.ItemEntity;
import com.rrecom.ecomsoft.io.ItemRequest;
import com.rrecom.ecomsoft.io.ItemResponse;
import com.rrecom.ecomsoft.repository.CategoryRepository;
import com.rrecom.ecomsoft.repository.ItemRepository;
import com.rrecom.ecomsoft.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImp implements ItemService {


    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        ItemEntity newItem = convertToEntity(request);

        CategoryEntity existingCategory = categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));
        newItem.setCategory(existingCategory);

        try {
            if (file != null && !file.isEmpty()) {
                // Convert MultipartFile to byte[] and set to imgUrl
                newItem.setImgUrl(file.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        }

        newItem = itemRepository.save(newItem);
        return convertToResponse(newItem);
    }


    private ItemEntity convertToEntity(ItemRequest request)
    {
      return   ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    private ItemResponse convertToResponse(ItemEntity newItem)
    {
      return   ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .price(newItem.getPrice())
                .imgUrl(newItem.getImgUrl())
                .categoryName(newItem.getCategory().getName())
                .categoryId(newItem.getCategory().getCategoryId())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
     return   itemRepository.findAll()
               .stream()
               .map(itemEntity -> convertToResponse(itemEntity))
               .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity item = itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found: " + itemId));
        itemRepository.delete(item);
    }

}
