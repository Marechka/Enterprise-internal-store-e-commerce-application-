package com.fnproject.wrstore.services;

import com.fnproject.wrstore.data.OrderDetailsRepository;
import com.fnproject.wrstore.models.OrderDetails;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})

public class OrderDetailsService {

    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }
//    public void addOrderedProducts(OrderDetails orderDetails) {
//        orderDetailsRepository.save(orderDetails);
//    }

    public List<OrderDetails> findAll() {
        return orderDetailsRepository.findAll();
    }

    // Make it search by Order number

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public OrderDetails findByEmployeeId(int id) throws NoSuchElementException{
        return orderDetailsRepository.findById(id).orElseThrow();
    }

    // saveOrUpdate()
    public void addOrderedProducts(OrderDetails orderDetails){
        log.info(orderDetails.toString());
        orderDetailsRepository.save(orderDetails);

    }
    public void delete(OrderDetails orderDetails){
        orderDetailsRepository.delete(orderDetails);
    }
}
