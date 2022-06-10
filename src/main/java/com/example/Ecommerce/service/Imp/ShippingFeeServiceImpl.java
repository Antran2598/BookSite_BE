package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.converter.ShippingFeeConverter;
import com.example.Ecommerce.dto.ShippingFeeDto;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.model.ShippingFee;
import com.example.Ecommerce.repository.ShippingFeeRepository;
import com.example.Ecommerce.service.ShippingFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShippingFeeServiceImpl implements ShippingFeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingFeeServiceImpl.class);

    @Autowired
    ShippingFeeRepository shippingFeeRepository;
    @Autowired
    ShippingFeeConverter shippingFeeConverter;


    @Override
    public List<ShippingFeeDto> getShipListToShow() {
        List<ShippingFeeDto> listDTO;
        try {
            List<ShippingFee> shippingFee = shippingFeeRepository.findAll();
            listDTO = shippingFeeConverter.toDTOList(shippingFee);
        } catch (Exception e) {
            LOGGER.info("Having error when load list author" + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_LIST_LOADED_FAIL);
        }
        return listDTO;
    }
}
