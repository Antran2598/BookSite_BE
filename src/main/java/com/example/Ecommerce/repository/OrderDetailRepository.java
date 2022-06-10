package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query(
            value = "select * from order_detail where order_id = ?1",
            nativeQuery = true
    )
    List<OrderDetail> getListItemInOrder(Long orderid);

    @Modifying
    @Transactional
    @Query(
            value = "Update order_detail set amount = ?2 where id = ?1",
            nativeQuery = true
    )
    int updateQuantityItem(Long id, int quantity);
}