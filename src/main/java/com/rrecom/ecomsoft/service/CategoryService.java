package com.rrecom.ecomsoft.service;

import com.rrecom.ecomsoft.io.CategoryRequest;
import com.rrecom.ecomsoft.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryResponse add(CategoryRequest request, MultipartFile file) throws Exception;

    List<CategoryResponse> read();

    void  delete(String categoryId);
}
