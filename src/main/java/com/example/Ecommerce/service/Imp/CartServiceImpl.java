package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.model.Cart;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.CartRepository;
import com.example.Ecommerce.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    CartRepository cartRepository;

    @Override
    public Cart getCartOfCustomer(User user) throws DataNotFoundException {
        return cartRepository.findByUser(user);
    }

    @Override
    public Boolean createCart(Cart cart) throws CreateDataFailException {
        boolean result;
        try {
            cartRepository.save(cart);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when create cart " + e.getMessage());
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_CART_FAIL);
        }
        return result;
    }

    @Override
    public Boolean deleteCart(Long cartID) throws DataNotFoundException, DeleteDataFailException {
        boolean result;
        try {
            Optional<Cart> cartOptional = cartRepository.findById(cartID);
            if (!cartOptional.isPresent()) {
                LOGGER.info("Can't find cart with id " + cartID);
                throw new DataNotFoundException(ErrorCode.ERR_CART_NOT_FOUND);
            }
            cartRepository.deleteCartId(cartID);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when delete cart " + e.getMessage());
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_CART_FAIL);
        }
        return result;
    }

}
