package com.fnproject.wrstore.controllers;

import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.Order;
import com.fnproject.wrstore.services.EmployeeService;
import com.fnproject.wrstore.services.OrderService;
import com.fnproject.wrstore.services.ProductService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "orders")
public class OrderController {
    EmployeeService employeeService;
    OrderService orderService;

    ProductService productService;

    @Autowired
    public OrderController(EmployeeService employeeService, OrderService orderService, ProductService productService) {
        this.employeeService = employeeService;
        this.orderService = orderService;
        this.productService  = productService;
    }

    @GetMapping
    public String getAllOrders(Model model){
        model.addAttribute("orders",orderService.findAll());
        return "orders";
    }

    @GetMapping (value = "/neworder")
    public String newOrder(Model model) {
        model.addAttribute("neworderproducts", productService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        return "neworder";
    }

//    @PostMapping("/saveordertouser/{employeeId}")
//    public String saveOrderToEmployee(@PathVariable int id, Model model){
//       // log.warn("model id: "+ email);
//        // check course is on the list
//        Employee employee = employeeService.findByEmployeeId(id);
//        Order order = new Order(employee);
//        orderService.save(order);
//
//        model.addAttribute("employee",employeeService.findByEmployeeId(id));
//
//        boolean isEmployeeNumberValid = employeeService.findAll().stream().anyMatch(course -> course.getName().equals(name));
//        if(isCourseNameValid){
//            try {
//                studentService.addCourse(email, courseService.findCourseByName(name));
//                model.addFlashAttribute("message", String.format("Persist %s to %s", name,email));
//                log.info(String.format("Persist %s to %s", name,email));
//            }catch(RuntimeException ex){
//                model.addFlashAttribute("message", String.format("Couldn't persist %s to %s", name,email));
//                log.error(String.format("Couldn't persist %s to %s", name,email));
//                ex.printStackTrace();
//            }
//        } else {
//            model.addFlashAttribute("message", String.format("Couldn't persist %s to %s because course don't exist", name,email));
//            log.warn(String.format("Couldn't persist %s to %s because course doesn't exist", name,email));
//        }
//        log.info("courses-choice:" + name);
//        log.info("isCourseNameValid: "+ isCourseNameValid);
//
//        return "redirect:/students";
//    }


}
