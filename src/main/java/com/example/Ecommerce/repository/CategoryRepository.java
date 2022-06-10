package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.Author;
import com.example.Ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM category",nativeQuery = true)
    List<Category> getAllCates();

    @Query(
            value = "SELECT * FROM category where cate_name = ?1",
            nativeQuery = true
    )
    Category checkExistedCategory(String cateName);

    @Query(
            value = "SELECT * FROM category where ID = ?1",
            nativeQuery = true
    )
    Category checkExistedCategoryId(Long id);

    @Modifying
    @Transactional
    @Query(
            value = "delete from category where ID = ?1",
            nativeQuery = true
    )
    int deleteCategoryId(Long id);
}