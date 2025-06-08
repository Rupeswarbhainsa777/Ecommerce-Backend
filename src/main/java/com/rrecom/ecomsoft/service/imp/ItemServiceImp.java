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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.*;
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
        try {
            String fileName = UUID.randomUUID().toString() + "." +
                    StringUtils.getFilenameExtension(file.getOriginalFilename());

            Path uploadPath = Paths.get("upload").toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            
            String imgUrl = "http://localhost:9091/api/v1.0/upload/" + fileName;



            CategoryEntity existingCategory = categoryRepository.findByCategoryId(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));

            ItemEntity newItem = convertToEntity(request);
            newItem.setCategory(existingCategory);
            newItem.setImgUrl(imgUrl);

            newItem = itemRepository.save(newItem);
            return convertToResponse(newItem);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file", e);
        }
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
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
        return itemRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity existingItem = itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found: " + itemId));

        String imgUrl = existingItem.getImgUrl();
        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
        Path uploadPath = Paths.get("upload").toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName);

        try {
            Files.deleteIfExists(filePath);
            itemRepository.delete(existingItem);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete the image", e
            );
        }
    }
}




//    @Override
//    public void deleteItem(String itemId) {
//        ItemEntity item = itemRepository.findByItemId(itemId)
//                .orElseThrow(() -> new RuntimeException("Item not found: " + itemId));
//        itemRepository.delete(item);
//    }


