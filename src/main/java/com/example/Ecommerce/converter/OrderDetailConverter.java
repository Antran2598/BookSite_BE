package com.example.Ecommerce.converter;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.OrderDetailDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.model.OrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrderDetailConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailConverter.class);

    @Autowired
    private ModelMapper modelMapper;

    public OrderDetailDto convertOrderDetailToDTO(OrderDetail orderDetail) {
        try {
            OrderDetailDto dto = modelMapper.map(orderDetail, OrderDetailDto.class);
            return dto;
        } catch (Exception e) {
            LOGGER.info("Fail to convert Account to AccountDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public OrderDetail convertOrderDetailToEntity(OrderDetailDto orderDetailDTO) {
        try {
            OrderDetail entity = modelMapper.map(orderDetailDTO, OrderDetail.class);
            return entity;
        } catch (Exception e) {
            LOGGER.info("Fail to convert Account to AccountDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

}
