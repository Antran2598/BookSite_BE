package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.converter.OrderConverter;
import com.example.Ecommerce.dto.OrderDto;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.model.Order;
import com.example.Ecommerce.repository.OrderRepository;
import com.example.Ecommerce.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderConverter orderConverter;

    @Override
    public List<Order> findAll() throws DataNotFoundException {
        List<Order> theListOrder;
        try {
            theListOrder = orderRepository.findAll();
        } catch (Exception e) {
            LOGGER.info("Having error when load the list order: " + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_ACCOUNT_ORDER_LIST_LOADED_FAIL);
        }
        return theListOrder;
    }

    @Override
    public Boolean createNewOrder(OrderDto newUserOrderDTO) throws CreateDataFailException {
        boolean result;
        try {
            Order order = orderConverter.convertAccountOrderToEntity(newUserOrderDTO);
            orderRepository.save(order);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when create a new order: " + e.getMessage());
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_ACCOUNT_ORDER_FAIL);
        }
        return result;
    }

    @Override
    public Order getAccountOrder(Long userId) throws DataNotFoundException {
        return orderRepository.getAccountOrderByAccountAndTotalPrice(userId);
    }
}
