package com.example.Ecommerce.service;

import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.exception.UpdateDataFailException;
import com.example.Ecommerce.model.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> getListItemOfCart(Long cartID) throws DataNotFoundException;

    CartItem checkCartItem(Long cartID, Long productID) throws DataNotFoundException;

    Boolean createNewItemInCart(CartItem cartItem) throws CreateDataFailException;

    Boolean updateNewItemInCart(CartItem cartItem) throws DataNotFoundException, UpdateDataFailException;

    Boolean deleteCartItem(Long itemID) throws DataNotFoundException, DeleteDataFailException;

    Boolean deleteAllItemInCart(Long cartId) throws DeleteDataFailException;
}
