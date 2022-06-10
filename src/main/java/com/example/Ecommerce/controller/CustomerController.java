package com.example.Ecommerce.controller;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.constants.SuccessCode;
import com.example.Ecommerce.converter.CartConverter;
import com.example.Ecommerce.dto.*;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.exception.UpdateDataFailException;
import com.example.Ecommerce.model.*;
import com.example.Ecommerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/auth/customer")
public class CustomerController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartConverter cartConverter;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/all/details/{bookId}")
    public ResponseEntity<ResponseDTO> getBook(@PathVariable("bookId") int bookId) {
        ResponseDTO response = new ResponseDTO();
        try {
            Long id = Long.valueOf(String.valueOf(bookId));
            DetailBookDto dto = bookService.getBookDetailByID(id);
            if (dto != null) {
                response.setData(dto);
                response.setSuccessCode(SuccessCode.BOOK_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_BOOK_LOADED_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_BOOK_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/addToCart")
    public ResponseEntity<ResponseDTO> addItemToCart(@RequestBody AddCartItemDto dto) throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long userID = dto.getUserId();
            User user = userService.getUserDetail(userID);
            Cart cart = cartService.getCartOfCustomer(user);
            if (cart != null) {
                CartItem item = new CartItem();
                Book book = bookService.getBookToAdd(dto.getItemId());
                item.setCart(cart);
                item.setProduct(book);
                item.setPrice(book.getSalePrice());
                item.setAmount(1);
                boolean addToCart = cartItemService.createNewItemInCart(item);
                if (addToCart) {
                    response.setData(true);
                    response.setSuccessCode(SuccessCode.CART_CREATE_SUCCESS);
                } else {
                    response.setErrorCode(ErrorCode.ERR_ADD_ITEM_CART_FAIL);
                    throw new CreateDataFailException(ErrorCode.ERR_ADD_ITEM_CART_FAIL);
                }
            } else {
                Cart newCart = new Cart();
                newCart.setUser(user);
                cartService.createCart(newCart);
                Book book = bookService.getBookToAdd(dto.getItemId());
                CartItem item = new CartItem();
                Cart cartCreated = cartService.getCartOfCustomer(user);
                item.setCart(cartCreated);
                item.setProduct(book);
                item.setPrice(book.getSalePrice());
                item.setAmount(1);
                boolean addToCart = cartItemService.createNewItemInCart(item);
                if (addToCart) {
                    response.setData(true);
                    response.setSuccessCode(SuccessCode.CART_CREATE_SUCCESS);
                } else {
                    response.setErrorCode(ErrorCode.ERR_ADD_ITEM_CART_FAIL);
                    throw new CreateDataFailException(ErrorCode.ERR_ADD_ITEM_CART_FAIL);
                }
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_CREATE_CART_FAIL);
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_CART_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/updateToCart")
    public ResponseEntity<ResponseDTO> updateCart(@RequestBody UpdateCartItemDto dto) throws UpdateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long userID = dto.getUserId();
            User user = userService.getUserDetail(userID);
            Cart cart = cartService.getCartOfCustomer(user);
            if (cart != null) {
                CartItem cartItem = cartItemService.checkCartItem(cart.getId(), dto.getItemId());
                if (cartItem == null) {
                    response.setErrorCode(ErrorCode.ERR_ITEM_CART_NOT_FOUND);
                    throw new DataNotFoundException(ErrorCode.ERR_ITEM_CART_NOT_FOUND);
                } else {
                    cartItem.setAmount(dto.getAmount());
                    boolean checkUpdate = cartItemService.updateNewItemInCart(cartItem);
                    if (checkUpdate) {
                        response.setSuccessCode(SuccessCode.CART_UPDATE_SUCCESS);
                        response.setData(true);
                    } else {
                        response.setErrorCode(ErrorCode.ERR_UPDATE_ITEM_CART_FAIL);
                        throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_ITEM_CART_FAIL);
                    }
                }
            } else {
                response.setErrorCode(ErrorCode.ERR_CART_NOT_FOUND);
                throw new DataNotFoundException(ErrorCode.ERR_CART_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_UPDATE_CART_FAIL);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_CART_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/removeItem/{itemId}")
    public ResponseEntity<ResponseDTO> removeItemToCart(@PathVariable("itemId") int itemId) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long id = Long.valueOf(String.valueOf(itemId));
            boolean resultDelete = cartItemService.deleteCartItem(id);
            if (resultDelete) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.CART_DELETE_SUCCESS);
            } else {
                response.setErrorCode(ErrorCode.ERR_REMOVE_ITEM_CART_FAIL);
                throw new DeleteDataFailException(ErrorCode.ERR_REMOVE_ITEM_CART_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_DELETE_CART_FAIL);
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_CART_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/clearCart/{userID}")
    public ResponseEntity<ResponseDTO> clearCart(@PathVariable("userID") int userID) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long id = Long.valueOf(String.valueOf(userID));
            User user = userService.getUserDetail(id);
            Cart cart = cartService.getCartOfCustomer(user);
            boolean result1 = cartItemService.deleteAllItemInCart(cart.getId());
            boolean result2 = cartService.deleteCart(cart.getId());
            if (result1 && result2) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.CART_CLEAR_SUCCESS);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_DELETE_CART_FAIL);
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_CART_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/cart/user/{userID}")
    public ResponseEntity<ResponseDTO> getCartOfCustomer(@PathVariable("userID") int userID) {
        ResponseDTO response = new ResponseDTO();
        try {
            Long id = Long.valueOf(String.valueOf(userID));
            User user = userService.getUserDetail(id);
            Cart cart = cartService.getCartOfCustomer(user);
            List<CartItem> listItem = cartItemService.getListItemOfCart(cart.getId());
            cart.setListItem(listItem);
            CartDto dto = cartConverter.convertCartToDto(cart);
            response.setData(dto);
            response.setSuccessCode(SuccessCode.GET_CART_SUCCESS);
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_CART_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_CART_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/order")
    public ResponseEntity<ResponseDTO> createOrderAccount(@RequestBody OrderDto dto) throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result;
            User user = userService.getUserDetail(dto.getUserId());
            Cart cart = cartService.getCartOfCustomer(user);
            List<CartItem> listCartItem = cartItemService.getListItemOfCart(cart.getId());
            Double totalPrice = Double.valueOf(0);
            for (CartItem cartItem : listCartItem) {
                totalPrice += cartItem.getPrice() * cartItem.getAmount();
            }
            dto.setTotalPrice(Double.parseDouble(String.valueOf(totalPrice)));
            result = orderService.createNewOrder(dto);
            Order order = orderService.getAccountOrder(user.getId());
            if (order!=null) {
                List<OrderDetail> listDetailOfOrder = new ArrayList<>();
                for (CartItem cartItem : listCartItem) {
                    OrderDetail detail = new OrderDetail();
                    detail.setOrder(order);
                    detail.setBook_id(cartItem.getProduct().getId());
                    detail.setAmount(cartItem.getAmount());
                    detail.setPrice(cartItem.getPrice());
                    listDetailOfOrder.add(detail);
                }
                for (OrderDetail orderDetail : listDetailOfOrder) {
                    orderDetailService.createOrderdetail(orderDetail);
                }
            } else {
                response.setErrorCode(ErrorCode.ERR_ACCOUNT_ORDER_NOT_FOUND);
                throw new DataNotFoundException(ErrorCode.ERR_ACCOUNT_ORDER_NOT_FOUND);
            }
            boolean result1 = cartItemService.deleteAllItemInCart(cart.getId());
            boolean result2 = cartService.deleteCart(cart.getId());
            if (result & result1 & result2) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.ORDER_CREATE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_CREATE_ACCOUNT_ORDER_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_CREATE_ACCOUNT_ORDER_FAIL);
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_ACCOUNT_ORDER_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

}
