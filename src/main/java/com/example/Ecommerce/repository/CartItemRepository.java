package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(
            value = "select * from cart_item where cart_id = ?1",
            nativeQuery = true
    )
    List<CartItem> getListItem(Long id);

    @Modifying
    @Transactional
    @Query(
            value = "Update cart_item set amount = ?2 where ID = ?1",
            nativeQuery = true
    )
    int updateItemQuantity(Long id, int quantity);

    @Query(
            value = "select * from cart_item where cart_id = ?1 and product_id = ?2",
            nativeQuery = true
    )
    CartItem getByCartAndProduct(Long cartID, Long productID);

    @Modifying
    @Transactional
    @Query(
            value = "Delete from cart_item where product_id = ?1",
            nativeQuery = true
    )
    int deleteByItemId(Long id);

    @Query(
            value = "Select * from cart_item where product_id = ?1",
            nativeQuery = true
    )
    CartItem getByItemId(Long id);

    @Modifying
    @Transactional
    @Query(
            value = "Delete from cart_item where cart_id = ?1",
            nativeQuery = true
    )
    int deleteByCartId(Long id);
}