package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.OrderDto;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll() throws DataNotFoundException;

    Boolean createNewOrder(OrderDto newUserOrderDTO) throws CreateDataFailException;

//    Order getAccountOrder(Long accountId, Double totalPrice) throws DataNotFoundException;

    Order getAccountOrder(Long userId) throws DataNotFoundException;
}
