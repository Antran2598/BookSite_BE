package com.example.Ecommerce.service;



import com.example.Ecommerce.dto.CategoryDto;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.exception.UpdateDataFailException;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategoryListToShow();

    Boolean addNewCategory(CategoryDto categoryDto) throws CreateDataFailException;

    Boolean updateCategory(CategoryDto categoryDto) throws DataNotFoundException, UpdateDataFailException;

    Boolean deleteCategory(Long id) throws DataNotFoundException, DeleteDataFailException;
}
