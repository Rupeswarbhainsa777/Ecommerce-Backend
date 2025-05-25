package com.rrecom.ecomsoft.service;

import com.rrecom.ecomsoft.io.ItemRequest;
import com.rrecom.ecomsoft.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

          ItemResponse add(ItemRequest request , MultipartFile file);

          List<ItemResponse> fetchItems();

          void deleteItem(String itemId);
}
