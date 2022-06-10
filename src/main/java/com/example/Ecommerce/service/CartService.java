package com.example.Ecommerce.service;

import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.model.Cart;
import com.example.Ecommerce.model.User;

public interface CartService {
    Cart getCartOfCustomer(User user) throws DataNotFoundException;

    Boolean createCart(Cart cart) throws CreateDataFailException;

    Boolean deleteCart(Long cartID) throws DataNotFoundException, DeleteDataFailException;
}
