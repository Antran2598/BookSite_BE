package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.converter.PaymentMethodConverter;
import com.example.Ecommerce.dto.PaymentMethodDto;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.model.PaymentMethod;
import com.example.Ecommerce.repository.PaymentMethodRepository;
import com.example.Ecommerce.service.PaymentMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodServiceImpl.class);

    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    PaymentMethodConverter paymentMethodConverter;

    @Override
    public List<PaymentMethodDto> getPayListToShow() {
        List<PaymentMethodDto> listDTO;
        try {
            List<PaymentMethod> paymentMethod = paymentMethodRepository.findAll();
            listDTO = paymentMethodConverter.toDTOList(paymentMethod);
        } catch (Exception e) {
            LOGGER.info("Having error when load list author" + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_LIST_LOADED_FAIL);
        }
        return listDTO;
    }
}
