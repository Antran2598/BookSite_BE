package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.converter.CategoryConverter;
import com.example.Ecommerce.dto.CategoryDto;
import com.example.Ecommerce.exception.*;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.repository.CategoryRepository;
import com.example.Ecommerce.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryConverter categoryConverter;

    @Override
    public List<CategoryDto> getCategoryListToShow() {
        List<CategoryDto> listDTO;
        try {
            List<Category> category = categoryRepository.getAllCates();
            listDTO = categoryConverter.toDTOList(category);
        } catch (Exception e) {
            LOGGER.info("Having error when load list category" + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_CATEGORY_LIST_LOADED_FAIL);
        }
        return listDTO;
    }

    @Override
    public Boolean addNewCategory(CategoryDto categoryDto) throws CreateDataFailException {
        boolean result;
        try {
            Category category = categoryConverter.convertCateDTOToEntity(categoryDto);
            Category tempCategory = categoryRepository.checkExistedCategory(category.getCateName());
            if (tempCategory != null) {
                LOGGER.info("Category have been existed ");
                throw new DuplicateDataException(ErrorCode.ERR_CATEGORY_EXISTED);
            }
            categoryRepository.save(category);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when create new Category: " + e.getMessage());
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_CATEGORY_FAIL);
        }
        return result;
    }

    @Override
    public Boolean updateCategory(CategoryDto categoryDto) throws DataNotFoundException, UpdateDataFailException {
        boolean result;
        try {
            Category category = categoryConverter.convertCateDTOToEntity(categoryDto);
            Category tempCategory = categoryRepository.checkExistedCategoryId(category.getId());
            if (tempCategory == null) {
                LOGGER.info("Can't find category ");
                throw new DataNotFoundException(ErrorCode.ERR_CATEGORY_NOT_FOUND);
            }
            categoryRepository.save(category);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when update category: " + e.getMessage());
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_CATEGORY_FAIL);
        }
        return result;
    }

    @Override
    public Boolean deleteCategory(Long id) throws DataNotFoundException, DeleteDataFailException {
        boolean result;
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (!categoryOptional.isPresent()) {
                LOGGER.info("Can't find category ");
                throw new DataNotFoundException(ErrorCode.ERR_CATEGORY_NOT_FOUND);
            }
            categoryRepository.deleteCategoryId(id);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when delete category " + e.getMessage());
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_CATEGORY_FAIL);
        }
        return result;
    }
}
