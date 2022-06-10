package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.model.OrderDetail;
import com.example.Ecommerce.repository.OrderDetailRepository;
import com.example.Ecommerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    @Autowired
    OrderDetailRepository orderdetailrepository;

    @Override
    public Boolean createOrderdetail(OrderDetail theOrderdetail) throws CreateDataFailException {
        boolean result;
        try {
            orderdetailrepository.save(theOrderdetail);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when create new order details " + e.getMessage());
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_ORDER_DETAIL_FAIL);
        }
        return result;
    }
}
