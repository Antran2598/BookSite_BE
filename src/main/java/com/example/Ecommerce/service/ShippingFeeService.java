package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.ShippingFeeDto;

import java.util.List;

public interface ShippingFeeService {
    List<ShippingFeeDto> getShipListToShow();
}
