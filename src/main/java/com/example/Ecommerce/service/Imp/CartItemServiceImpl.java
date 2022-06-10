package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.exception.UpdateDataFailException;
import com.example.Ecommerce.model.CartItem;
import com.example.Ecommerce.repository.CartItemRepository;
import com.example.Ecommerce.service.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> getListItemOfCart(Long cartID) throws DataNotFoundException {
        List<CartItem> cartItem;
        try {
            cartItem = cartItemRepository.getListItem(cartID);
        } catch (Exception e) {
            LOGGER.info("Having error when  find item in cart: " + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_ITEM_CART_NOT_FOUND);
        }
        return cartItem;
    }

    @Override
    public CartItem checkCartItem(Long cartID, Long productID) throws DataNotFoundException {
        CartItem item;
        try {
            item = cartItemRepository.getByCartAndProduct(cartID, productID);
        } catch (Exception e) {
            LOGGER.info("Having error when  find item in cart " + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_ITEM_CART_NOT_FOUND);
        }
        return item;
    }

    @Override
    public Boolean createNewItemInCart(CartItem cartItem) throws CreateDataFailException {
        boolean result;
        try {
            CartItem cartItemCheck = cartItemRepository.getByCartAndProduct(cartItem.getCart().getId(), cartItem.getProduct().getId());
            if (cartItemCheck != null) {
                LOGGER.info("Item have been in cart ");
                throw new DataNotFoundException(ErrorCode.ERR_DUPLICATE_ITEM_CART);
            }
            cartItemRepository.save(cartItem);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when create new item in cart id " + e.getMessage());
            throw new CreateDataFailException(ErrorCode.ERR_ADD_ITEM_CART_FAIL);
        }
        return result;
    }

    @Override
    public Boolean updateNewItemInCart(CartItem cartItem) throws DataNotFoundException, UpdateDataFailException {
        boolean result;
        try {
            CartItem cartItemCheck = cartItemRepository.getByCartAndProduct(cartItem.getCart().getId(), cartItem.getProduct().getId());
            if (cartItemCheck == null) {
                LOGGER.info("Item can't found in cart ");
                throw new DataNotFoundException(ErrorCode.ERR_ITEM_CART_NOT_FOUND);
            }
            cartItemRepository.save(cartItem);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when update item in cart " + e.getMessage());
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_CART_FAIL);
        }
        return result;
    }

    @Override
    public Boolean deleteCartItem(Long itemID) throws DataNotFoundException, DeleteDataFailException {
        boolean result = false;
        try {
            CartItem cartItem = cartItemRepository.getByItemId(itemID);
            CartItem cartItemCheck = cartItemRepository.getByCartAndProduct(cartItem.getCart().getId(), cartItem.getProduct().getId());
            if (cartItemCheck == null) {
                LOGGER.info("Item can't found in cart ");
                throw new DataNotFoundException(ErrorCode.ERR_ITEM_CART_NOT_FOUND);
            }
            int resultDelete = cartItemRepository.deleteByItemId(itemID);
            if (resultDelete > 0) {
                result = true;
            }
        } catch (Exception e) {
            LOGGER.info("Having error when update item  " + e.getMessage());
            throw new DeleteDataFailException(ErrorCode.ERR_REMOVE_ITEM_CART_FAIL);
        }
        return result;
    }

    @Override
    public Boolean deleteAllItemInCart(Long cartId) throws DeleteDataFailException {
        boolean result;
        try {
            cartItemRepository.deleteByCartId(cartId);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when update item quantity in cart " + e.getMessage());
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_CART_FAIL);
        }
        return result;
    }

}
