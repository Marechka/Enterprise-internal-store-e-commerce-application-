package com.fnproject.wrstore.controllers;

import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.Order;
import com.fnproject.wrstore.models.OrderDetails;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
        this.productService = productService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());

        // model.addAttribute("employee", new Employee())
        return "orders";
    }

    @PostMapping("/generateneworder")
    public String generateNewOrder(@RequestParam(required = false) String id, RedirectAttributes redirectAttributes) {
        log.warn("employee id: " + id);
        try {
            redirectAttributes.addFlashAttribute("employee", employeeService.findByEmployeeId(Integer.parseInt(id)));
            redirectAttributes.addFlashAttribute("products", productService.findAll());
            Order order = new Order(employeeService.findByEmployeeId(Integer.parseInt(id)));
            orderService.save(order);
            log.warn("order after save " + order);
            Order orderId = orderService.findById(order.getId());
            log.warn("Order id: " + orderId.toString());
            redirectAttributes.addFlashAttribute("order", orderId);
            OrderDetails orderDetails = new OrderDetails();
           // orderDetails.setOrder(orderId);
            redirectAttributes.addFlashAttribute("orderdetails", orderDetails);
            log.warn("Passing order into orderdetails" + orderId);
        } catch (Exception ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("employee_not_found", String.format("Employee: %s not found!", id));
            return "redirect:/orders";
        }
        return "redirect:/orders/gettoneworder";
    }

//    @GetMapping ("/gettoneworder")
//    public String newOrder(RedirectAttributes redirectAttribute, @ModelAttribute("employee") Employee employee){
//        log.warn("employee : " + employee);
//        try {
//           /* Order order = new Order(employee);
//            orderService.save(order);
//            Order orderId = orderService.findById(order.getId());
//            redirectAttribute.addFlashAttribute("order", orderId);
//            log.warn("Order id: " + orderId);*/
//        } catch (Exception ex){
//            ex.printStackTrace();
//            redirectAttribute.addFlashAttribute("employee_not_found",String.format("Employee: %s not found!",employee.getId()));
//            return "redirect:/orders";
//        }
//        return "redirect:/orders";
//
//    }

    @GetMapping("/gettoneworder")
        public String getToNewOrder() {
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
