package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.PaymentMethodDto;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodDto> getPayListToShow();
}
