package service.imp;

import com.rrecom.ecomsoft.entity.CategoryEntity;
import com.rrecom.ecomsoft.io.CategoryRequest;
import com.rrecom.ecomsoft.io.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.CategoryService;
import com.rrecom.ecomsoft.entity.CategoryEntity;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    @Override
    public CategoryResponse add(CategoryRequest request) {
        CategoryEntity newCategory = convertToEntity(request);

    }

    private CategoryEntity convertToEntity(CategoryRequest request) {

        CategoryEntity.
    }
}
