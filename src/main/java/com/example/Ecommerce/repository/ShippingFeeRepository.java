package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.ShippingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingFeeRepository extends JpaRepository<ShippingFee, Long> {

}