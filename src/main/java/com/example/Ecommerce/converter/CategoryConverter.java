package com.example.Ecommerce.converter;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.CategoryDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryConverter.class);
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto convertCategoryToDTO(Category category) {
        try {
            CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
            return categoryDto;
        } catch (Exception e) {
            LOGGER.info("Fail to convert Category to CategoryDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public Category convertCateDTOToEntity(CategoryDto dtoCategory) {
        try {
            Category category = modelMapper.map(dtoCategory, Category.class);
            return category;
        } catch (Exception e) {
            LOGGER.info("Fail to convert CategoryDTO to Category");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public CategoryDto toDTO(Category entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setCateName(entity.getCateName());
        return dto;
    }

    public List<CategoryDto> toDTOList(List<Category> entityList) {
        List<CategoryDto> dtoList = entityList.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
        return dtoList;
    }
}
