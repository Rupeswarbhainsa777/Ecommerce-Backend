package service;

import com.rrecom.ecomsoft.io.CategoryRequest;
import com.rrecom.ecomsoft.io.CategoryResponse;

public interface CategoryService {

    CategoryResponse add(CategoryRequest request);
}
