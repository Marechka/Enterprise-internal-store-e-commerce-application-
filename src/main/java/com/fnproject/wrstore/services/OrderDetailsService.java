package com.fnproject.wrstore.services;

import com.fnproject.wrstore.data.OrderDetailsRepository;
import com.fnproject.wrstore.data.OrderRepository;
import com.fnproject.wrstore.models.Order;
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
    OrderRepository orderRepository;

    @Autowired
    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository, OrderRepository orderRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public List<OrderDetails> findByOrderId(int id) throws NoSuchElementException{
        return orderDetailsRepository.findOrderDetailsByOrderId(id);
    }

//    // saveOrUpdate()
//    public void addOrderedProduct(int orderId, OrderDetails orderDetails){
//        log.info(orderDetails.toString());
//        orderDetailsRepository.save(orderDetails);
//        Order order = orderRepository.findById(orderId).orElseThrow();
//      //  order.addOrderDetails(orderDetails);
//       // orderRepository.getOrder().add(order);
//
//    }

    public void saveOrUpdate(OrderDetails orderDetails) {
        orderDetailsRepository.save(orderDetails);
    }
    public void delete(OrderDetails orderDetails){
        orderDetailsRepository.delete(orderDetails);
    }
}


