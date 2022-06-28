package com.fnproject.wrstore.services;

import com.fnproject.wrstore.data.EmployeeRepository;
import com.fnproject.wrstore.data.OrderRepository;
import com.fnproject.wrstore.models.Order;
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
public class OrderService {

    OrderRepository orderRepository;
    EmployeeRepository employeeRepository;
@Autowired
    public OrderService(OrderRepository orderRepository, EmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
    }


    public List<Order> findAll(){
        return orderRepository.findAll();
    }
    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Order findById(int id) throws NoSuchElementException{
        return orderRepository.findById(id).orElseThrow();
    }

    public void saveOrUpdate(Order order, int id){
        order.setEmployee(employeeRepository.findById(id).get());
        orderRepository.save(order);
        log.info(String.format("Order ID created: %d Order Employee Name: %s",order.getOrderId(),order.getEmployee()));
    }

    public void delete(Order order){
        orderRepository.delete(order);
    }

}
