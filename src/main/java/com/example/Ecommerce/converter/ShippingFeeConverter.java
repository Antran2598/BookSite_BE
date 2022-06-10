package com.example.Ecommerce.converter;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.AuthorDto;
import com.example.Ecommerce.dto.ShippingFeeDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.model.Author;
import com.example.Ecommerce.model.ShippingFee;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ShippingFeeConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingFeeConverter.class);
    @Autowired
    private ModelMapper modelMapper;

    public ShippingFee convertAuthorDTOToEntity(ShippingFeeDto dtoShip) {
        try {
            ShippingFee shippingFee = modelMapper.map(dtoShip, ShippingFee.class);
            return shippingFee;
        } catch (Exception e) {
            LOGGER.info("Fail to convert AuthorDTO to Book");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public ShippingFeeDto toDTO(ShippingFee entity) {
        ShippingFeeDto dto = new ShippingFeeDto();
        dto.setId(entity.getId());
        dto.setLocation(entity.getLocation());
        return dto;
    }

    public List<ShippingFeeDto> toDTOList(List<ShippingFee> entityList) {
        List<ShippingFeeDto> dtoList = entityList.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
        return dtoList;
    }
}
