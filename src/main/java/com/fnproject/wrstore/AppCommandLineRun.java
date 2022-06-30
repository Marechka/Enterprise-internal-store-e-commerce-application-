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

    CartService cartService;

    @Autowired
    public AppCommandLineRun(EmployeeService employeeService, OrderService orderService, ProductService  productService, OrderDetailsService orderDetailsService, CartService cartService) {
        this.employeeService = employeeService;
        this.orderService = orderService;
        this.productService = productService;
        this.orderDetailsService = orderDetailsService;
        this.cartService = cartService;
    }

    @PostConstruct
    public void postConstruct(){
        log.warn("============ Application CommandLine Runner ============");
    }

    @Override
    public void run(String... args) throws Exception {


        Employee emp1 = new Employee(0112, "Masha", "Volska", new SimpleDateFormat("MM/dd/yyyy").parse("01/15/2022") );
        log.info("Before save" + emp1);
        employeeService.saveOrUpdate(emp1);
        log.info("After save" + emp1);
        employeeService.saveOrUpdate(new Employee( 0113, "Tania", "Yanushkevych", new SimpleDateFormat("MM/dd/yyyy").parse("01/15/2022") ));

        Product pr1 = new Product("Juice 5 oz", 2.50, "Ocean Spray");
        productService.saveOrUpdate(pr1 );
        Product pr2 =  new Product("Coke", 3.00, "Coca-Cola");
        productService.saveOrUpdate(pr2);

       // Cart cart1 = new Cart(pr1, 2, emp1);
      cartService.addToCart1(pr1, 2, emp1);
      orderService.placeOrder(emp1);




        //Order or1 = new Order(new SimpleDateFormat("MM/dd/yyyy").parse("06/25/2022"));
        // Order or1 = new Order(java.time.LocalDate.now()));
        //orderService.saveOrUpdate(or1, emp1.getEmployeeId());
//
//        orderDetailsService.saveOrUpdate( new OrderDetails(new OrderDetailsID(or1, pr1), 2, 5));
    }
}
