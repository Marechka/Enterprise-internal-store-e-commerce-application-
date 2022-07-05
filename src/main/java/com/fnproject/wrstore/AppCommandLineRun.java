package com.fnproject.wrstore;

import com.fnproject.wrstore.models.*;
import com.fnproject.wrstore.services.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AppCommandLineRun implements CommandLineRunner {

    EmployeeService employeeService;
    OrderService orderService;
    ProductService productService;
    OrderDetailsService orderDetailsService;

    @Autowired
    public AppCommandLineRun(EmployeeService employeeService, OrderService orderService, ProductService  productService, OrderDetailsService orderDetailsService) {
        this.employeeService = employeeService;
        this.orderService = orderService;
        this.productService = productService;
        this.orderDetailsService = orderDetailsService;
    }

    @PostConstruct
    public void postConstruct(){
        log.warn("============ Application CommandLine Runner ============");
    }

    @Override
    public void run(String... args) throws Exception {

//
//         Employee emp1 = new Employee(1, "Masha", "Volska", new SimpleDateFormat("MM/dd/yyyy").parse("01/15/2022") );
//       // Employee emp1 = new Employee(1, "Masha", "Volska", new Date);
//        log.info("Before save" + emp1);
//        employeeService.saveOrUpdate(emp1);
//        log.info("After save" + emp1);
//        employeeService.saveOrUpdate(new Employee( 2, "Tania", "Yanushkevych", new SimpleDateFormat("MM/dd/yyyy").parse("01/15/2022") ));
//
//
//
//        Product pr1 = new Product("Juice 5 oz", "Ocean Spray", 2.50);
//        productService.saveOrUpdate(pr1 );
//        Product pr2 =  new Product("Coke", "Coca-Cola",3.00);
//        productService.saveOrUpdate(pr2);
//
//
//        Order or1 = new Order(emp1);
//       orderService.save(or1);
//       //orderService.save(new Order(employeeService.findByEmployeeId(1)));
//
//        //OrderDetails ord1 = new OrderDetails(5, or1,pr1 );
//        //orderDetailsService.saveOrUpdate(ord1);
//
//       orderDetailsService.saveOrUpdate(new OrderDetails(5,orderService.findById(1), pr1));
//        Order or2 = new Order(emp1);
//        log.warn("Or2: " + or2);
//         orderService.save(or2);
//
//        orderService.placeOrder(or1);
//        orderService.placeOrder(orderService.findById(1));
//


        // Order or1 = new Order(java.time.LocalDate.now()));
        //orderService.saveOrUpdate(or1, emp1.getEmployeeId());
//
//        orderDetailsService.saveOrUpdate( new OrderDetails(new OrderDetailsID(or1, pr1), 2, 5));
    }
}
