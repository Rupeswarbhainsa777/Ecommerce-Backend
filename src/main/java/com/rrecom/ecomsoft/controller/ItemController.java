package com.rrecom.ecomsoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrecom.ecomsoft.io.ItemRequest;
import com.rrecom.ecomsoft.io.ItemResponse;
import com.rrecom.ecomsoft.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/items")
    public ItemResponse addItem(@RequestPart("item")String itemString,
                                @RequestPart("file")MultipartFile file)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ItemRequest itemRequest = null;
        try {
          itemRequest = objectMapper.readValue(itemString,ItemRequest.class);
              return itemService.add(itemRequest,file);
        }
        catch (JsonProcessingException ex)
        {
              throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error occured while processing the json");
        }

    }

    @GetMapping("/items")
    public List<ItemResponse> readItems()
    {
        return itemService.fetchItems();
    }



    @DeleteMapping("/admin/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable String itemId) {
        try {
            itemService.deleteItem(itemId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + itemId);
        }
    }
}
