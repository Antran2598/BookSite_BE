package com.example.Ecommerce.converter;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.OrderDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.model.Order;
import com.example.Ecommerce.model.PaymentMethod;
import com.example.Ecommerce.model.ShippingFee;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.PaymentMethodRepository;
import com.example.Ecommerce.repository.ShippingFeeRepository;
import com.example.Ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConverter.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private ShippingFeeRepository shippingFeeRepository;

    public OrderDto convertAccountOrderToDTO(Order order) {
        try {
            OrderDto dto = modelMapper.map(order, OrderDto.class);
            return dto;
        } catch (Exception e) {
            LOGGER.info("Fail to convert Account to AccountDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public Order convertAccountOrderToEntity(OrderDto dto) {
        try {
            PaymentMethod paymentMethod;
            ShippingFee shippingFee;
            Order order = modelMapper.map(dto, Order.class);
            User user = userService.getUserDetail(order.getUser().getId());
            Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(dto.getPaymentMethod());
            if (paymentMethodOptional.isPresent()) {
                paymentMethod = paymentMethodOptional.get();
            } else {
                LOGGER.info("Can't find payment method");
                throw new DataNotFoundException(ErrorCode.ERR_PAYMENT_NOT_FOUND);
            }
            Optional<ShippingFee> shippingFeeOptional = shippingFeeRepository.findById(dto.getShippingFee());
            if (shippingFeeOptional.isPresent()) {
                shippingFee = shippingFeeOptional.get();
            } else {
                LOGGER.info("Can't find shipping fee");
                throw new DataNotFoundException(ErrorCode.ERR_SHIPPING_FEE_NOT_FOUND);
            }
            order.setUser(user);
            order.setPaymentMethod(paymentMethod);
            order.setShippingFee(shippingFee);
            return order;
        } catch (Exception e) {
            LOGGER.info("Fail to convert Account to AccountDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

}
