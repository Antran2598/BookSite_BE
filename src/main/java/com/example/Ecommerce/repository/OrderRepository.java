package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(
            value = "select * from order where user_id = ?1",
            nativeQuery = true
    )
    List<Order> findByCustomerid(Long customerid);

    @Query(
            value = "select * from `order` where user_id = ?1 ",
            nativeQuery = true
    )
    Order getAccountOrderByAccountAndTotalPrice(Long userID);


}