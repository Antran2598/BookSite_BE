package com.example.Ecommerce.converter;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.PaymentMethodDto;
import com.example.Ecommerce.dto.ShippingFeeDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.model.PaymentMethod;
import com.example.Ecommerce.model.ShippingFee;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class PaymentMethodConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodConverter.class);
    @Autowired
    private ModelMapper modelMapper;

    public PaymentMethod convertAuthorDTOToEntity(PaymentMethodDto dtoPay) {
        try {
            PaymentMethod paymentMethod = modelMapper.map(dtoPay, PaymentMethod.class);
            return paymentMethod;
        } catch (Exception e) {
            LOGGER.info("Fail to convert AuthorDTO to Book");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public PaymentMethodDto toDTO(PaymentMethod entity) {
        PaymentMethodDto dto = new PaymentMethodDto();
        dto.setId(entity.getId());
        dto.setMethod(entity.getMethod());
        return dto;
    }

    public List<PaymentMethodDto> toDTOList(List<PaymentMethod> entityList) {
        List<PaymentMethodDto> dtoList = entityList.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
        return dtoList;
    }
}
