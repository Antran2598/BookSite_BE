package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.Cart;
import com.example.Ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
    @Modifying
    @Transactional
    @Query(
            value = "delete from cart where ID = ?1",
            nativeQuery = true
    )
    int deleteCartId(Long id);
}