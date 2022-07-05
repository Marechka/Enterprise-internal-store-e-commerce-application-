package com.fnproject.wrstore.controllers;

import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.OrderDetails;
import com.fnproject.wrstore.models.Product;
import com.fnproject.wrstore.services.OrderDetailsService;
import com.fnproject.wrstore.services.OrderService;
import com.fnproject.wrstore.services.ProductService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "orderdetails")
public class OrderDetailsController {

    OrderDetailsService orderDetailsService;
    OrderService orderService;
    ProductService productService;

    @Autowired
    public OrderDetailsController(OrderDetailsService orderDetailsService, OrderService orderService, ProductService productService) {
        this.orderDetailsService = orderDetailsService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping("/addtoorder/{id}/{prodId}")
    public String registerStudentToCourse(@ModelAttribute("orderdetails") OrderDetails orderDetails, @PathVariable("id") int id, @PathVariable("prodId") int prodId, Model model){
        //orderService.findById(orderId);
        //OrderDetails ordD = orderDetailsService.saveOrUpdate(orderDetails);

        orderDetails.setProduct(productService.findById(prodId));
        orderDetails.setOrder(orderService.findById(id));
        orderDetailsService.saveOrUpdate(orderDetails);

        log.warn("Setting up Order in OD: " + orderService.findById(id).toString());
        log.warn("Setting up Product in OD: " + productService.findById(prodId));
        log.warn("Setting up order details for order: " + orderDetails.toString());

        orderDetailsService.saveOrUpdate(orderDetails);

        log.warn("Submitting order: "+ orderDetails.getOrder());
        log.warn("Product in order details: " + orderDetails.getProduct());
        log.warn("Order details product QUANTITY: " + orderDetails.getQty());

        orderService.placeOrder(orderDetails.getOrder());
        return "redirect:/orders";

//        model.addAttribute("student",studentService.findByEmail(email));
//        // courses available to register
//        Set<Course> studentNotRegisteredToThisCourses = new HashSet<>(courseService.findAll());
//        studentNotRegisteredToThisCourses.removeAll(courseService.getStudentCourses(email));
//        log.info(studentNotRegisteredToThisCourses.toString());
//        model.addAttribute("studentNotRegisteredToThisCourses",studentNotRegisteredToThisCourses);
    }
//
//    @PostMapping("/saveupdateemployee")
//    public String saveUpdateEmployee(RedirectAttributes redirectAttribute, @ModelAttribute("employee") Employee employee){
//        log.warn("Model employee: "+ employee);
//        employeeService.saveOrUpdate(employee);
//        redirectAttribute.addFlashAttribute("employee",employeeService.findByEmployeeId(employee.getId()));
//        //model.addFlashAttribute("addedEmpId", employee.getId());
//        return "redirect:/employees";
//    }
}
