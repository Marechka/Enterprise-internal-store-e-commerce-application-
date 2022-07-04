package com.fnproject.wrstore.controllers;

import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.OrderDetails;
import com.fnproject.wrstore.models.Product;
import com.fnproject.wrstore.services.OrderDetailsService;
import com.fnproject.wrstore.services.OrderService;
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

    @Autowired
    public OrderDetailsController(OrderDetailsService orderDetailsService, OrderService orderService) {
        this.orderDetailsService = orderDetailsService;
        this.orderService = orderService;
    }

//    @PostMapping("/addtoorder")
//    public String registerStudentToCourse(@ModelAttribute("orderdetails") OrderDetails orderDetails, @ModelAttribute("order") Order order, Model model){
//        orderDetailsService.saveOrUpdate(orderDetails);
//        OrderDetails ordD = orderDetailsService.saveOrUpdate(orderDetails);
//        Order ord = order;
//        orderDetails.setOrder(ord);
//        log.warn("Setting up order in OD: " + order);
//       orderDetailsService.saveOrUpdate(orderDetails);
//       log.warn("Submitting order: "+ orderDetails.getOrder());
//       orderService.placeOrder(orderDetails.getOrder());
//       return "redirect:/orders";

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
