package com.example.Ecommerce.service;

import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.model.OrderDetail;

public interface OrderDetailService {
    Boolean createOrderdetail(OrderDetail theOrderdetail) throws CreateDataFailException;
}
