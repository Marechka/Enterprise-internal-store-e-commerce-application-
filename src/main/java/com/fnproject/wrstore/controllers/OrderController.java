package com.fnproject.wrstore.controllers;

import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.Order;
import com.fnproject.wrstore.models.OrderDetails;
import com.fnproject.wrstore.models.Product;
import com.fnproject.wrstore.services.EmployeeService;
import com.fnproject.wrstore.services.OrderDetailsService;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "orders")
public class OrderController {
    EmployeeService employeeService;
    OrderService orderService;

    ProductService productService;

    OrderDetailsService orderDetailsService;

    @Autowired
    public OrderController(EmployeeService employeeService, OrderService orderService, ProductService productService, OrderDetailsService orderDetailsService) {
        this.employeeService = employeeService;
        this.orderService = orderService;
        this.productService = productService;
        this.orderDetailsService = orderDetailsService;
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
//            redirectAttributes.addFlashAttribute("ordered", new ArrayList<Integer>());
           // redirectAttributes.addFlashAttribute("selectedproduct", new Object());
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

    @PostMapping("/addtoorder/{id}/{prodId}")
    public String registerStudentToCourse(RedirectAttributes redirectAttributes,  @ModelAttribute("orderdetails") OrderDetails orderDetails, @PathVariable("id") int id, @PathVariable("prodId") int prodId, Model model) {

        //orderService.findById(orderId);
        //OrderDetails ordD = orderDetailsService.saveOrUpdate(orderDetails);
//        for (int i = 1; i <= ordered.size(); i++) {
//            OrderDetails orderDetails = new OrderDetails();
//            if (ordered.get(i) !=0 ) {
//                orderDetails.setOrder(orderService.findById(id));
//                orderDetails.setQty(ordered.get(i));
//                orderDetails.setProduct(productService.findById(i));
//                orderDetailsService.saveOrUpdate(orderDetails);
//            }
//        }
        orderDetails.setProduct(productService.findById(prodId));
        orderDetails.setOrder(orderService.findById(id));
        orderDetailsService.saveOrUpdate(orderDetails);

        log.warn("Setting up Order in OD: " + orderService.findById(id).toString());
        log.warn("Setting up Product in OD: " + productService.findById(prodId));
        log.warn("Setting up order details for order: " + orderDetails.toString());

        //orderDetailsService.saveOrUpdate(orderDetails);

        log.warn("Submitting order: " + orderDetails.getOrder());
        log.warn("Product in order details: " + orderDetails.getProduct());
        log.warn("Order details product QUANTITY: " + orderDetails.getQty());
        //redirectAttributes.addFlashAttribute("addedorderdetails", orderDetails);
        redirectAttributes.addFlashAttribute("order", orderService.findById(id));
        redirectAttributes.addFlashAttribute("employee", orderService.findById(id).getEmployee());
        Set<Product> productsNotInOrder = new HashSet<>(productService.findAll());
        for ( OrderDetails orderD : orderDetailsService.findByOrderId(id)) {
            productsNotInOrder.remove(orderD.getProduct());
        }
        redirectAttributes.addFlashAttribute("products", productsNotInOrder);
        redirectAttributes.addFlashAttribute("orderdetails",  new OrderDetails());

        //orderService.placeOrder(orderDetails.getOrder());
        return "redirect:/orders/gettoneworder";
 //       return "redirect:/orders";
    }

    @GetMapping ("/submitorder/{id}")
    public String getToFinalOrder(RedirectAttributes redirectAttributes, @PathVariable("id") int id, Model model){
        Order order = orderService.findById(id);
        //redirectAttributes.addFlashAttribute("submittedorder", order);
        model.addAttribute("submittedorder", order);
        model.addAttribute("total", order);
        for (OrderDetails orderDetails : orderDetailsService.findByOrderId(id)) {
            Set<Product> orderProducts = new HashSet<>();
        }
        //redirectAttributes.addFlashAttribute("orderdetails", orderDetailsService.findByOrderId(id));
        model.addAttribute("orderdetails", orderDetailsService.findByOrderId(id));
        log.warn("OrderDetails found by ID: " + orderDetailsService.findByOrderId(id));
        orderService.placeOrder(order);
//        return "redirect:/orders/finalorder";
        return "completeorder";
    }

    @GetMapping("/finalorder")
    public String registerStudentToCourse()/*@ModelAttribute("orderdetails") OrderDetails orderDetails, RedirectAttributes redirectAttributes, @PathVariable("id") int id)*/{
        //Order order = orderService.findById(id);
       // orderService.placeOrder(order);
        //redirectAttributes.addFlashAttribute("submittedorder", order);
       // redirectAttributes.addFlashAttribute("order'sdetails", orderDetailsService.findByOrderId(id));
        return "completeorder";

    }
//        //orderService.findById(orderId);
//        //OrderDetails ordD = orderDetailsService.saveOrUpdate(orderDetails);
//
//        orderDetails.setProduct(productService.findById(prodId));
//        orderDetails.setOrder(orderService.findById(id));
//        orderDetailsService.saveOrUpdate(orderDetails);
//
//        log.warn("Setting up Order in OD: " + orderService.findById(id).toString());
//        log.warn("Setting up Product in OD: " + productService.findById(prodId));
//        log.warn("Setting up order details for order: " + orderDetails.toString());
//
//        orderDetailsService.saveOrUpdate(orderDetails);
//
//        log.warn("Submitting order: "+ orderDetails.getOrder());
//        log.warn("Product in order details: " + orderDetails.getProduct());
//        log.warn("Order details product QUANTITY: " + orderDetails.getQty());
//
//        orderService.placeOrder(orderDetails.getOrder());
//        return "redirect:/orders";



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
