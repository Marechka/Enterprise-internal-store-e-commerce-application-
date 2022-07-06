package com.fnproject.wrstore.services;

import com.fnproject.wrstore.data.EmployeeRepository;
import com.fnproject.wrstore.data.OrderDetailsRepository;
import com.fnproject.wrstore.data.OrderRepository;
import com.fnproject.wrstore.data.ProductRepository;
import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.Order;
import com.fnproject.wrstore.models.OrderDetails;
import com.fnproject.wrstore.models.Product;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
//@NoArgsConstructor
//@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class OrderService {

    EmployeeRepository employeeRepository;
    OrderRepository orderRepository;
    OrderDetailsRepository orderDetailsRepository;
    ProductRepository productRepository;
    @Autowired
    public OrderService(EmployeeRepository employeeRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository) {
        this.employeeRepository = employeeRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
    }

    // create new order
    public Order newOrder(int employeeId) {
        Order newOrder = new Order();
        Employee employee = employeeRepository.findById(employeeId);
        newOrder.setEmployee(employee);
        return newOrder;
    }

    // generates and places total amount of the order into finalized Order
    public void placeOrder (Order order) {
        int orderId = order.getId();
        List<OrderDetails> orderDetails = orderDetailsRepository.findOrderDetailsByOrderId(orderId);
        double total = 0;
        for (OrderDetails  details: orderDetails) {
            Product product = productRepository.findById(details.getProduct().getProdId()).orElseThrow();
            total+= details.getQty() * product.getPrice();
        }
        order.setTotalPrice(total);
        orderRepository.save(order);
    }

    public List<Order> listOrders(Employee employee) {
        return orderRepository.findAllByEmployeeOrderByDateDesc(employee);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Order findById(int id) throws NoSuchElementException{
        return orderRepository.findById(id).orElseThrow();
    }

    public void save(Order order){
        orderRepository.save(order);
        log.info(String.format("Order ID created: %d Order Employee Name: %s",order.getId(),order.getEmployee()));
    }

    public void delete(Order order){
        orderRepository.delete(order);
    }

}
