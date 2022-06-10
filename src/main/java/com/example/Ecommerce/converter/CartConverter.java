package com.example.Ecommerce.converter;


import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.CartDto;
import com.example.Ecommerce.dto.CartItemDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.model.Cart;
import com.example.Ecommerce.model.CartItem;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartConverter.class);
    @Autowired
    private ModelMapper modelMapper;

    public CartDto convertCartToDto(Cart cart) throws ConvertEntityDTOException {
        try {
            CartDto cartDto = new CartDto();
            cartDto.setId(cart.getId());
            cartDto.setUser(cart.getUser().getId());
            List<CartItemDto> cartItems = cart.getListItem().stream().map(this::convertCartItemToDto).collect(Collectors.toList());
            cartDto.setCartItems(cartItems);
            return cartDto;
        } catch (Exception ex) {
            LOGGER.info("Fail to convert Cart to CartDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public CartItemDto convertCartItemToDto(CartItem cartItem) throws ConvertEntityDTOException {
        try {
            CartItemDto cartItemDTO = new CartItemDto();
            cartItemDTO.setId(cartItem.getId());
            cartItemDTO.setItemId(cartItem.getProduct().getId());
            cartItemDTO.setPrice(cartItem.getPrice());
            cartItemDTO.setAmount(cartItem.getAmount());
            return cartItemDTO;
        } catch (Exception ex) {
            LOGGER.info("Fail to convert CartItem to CartItemDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }
}
