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
        return "orders";
    }

    @PostMapping("/generateneworder")
    public String generateNewOrder(@RequestParam(required = false) String id, RedirectAttributes redirectAttributes) {
        log.warn("employee id: " + id);
        try {
            redirectAttributes.addFlashAttribute("employee", employeeService.findByEmployeeId(Integer.parseInt(id)));
            redirectAttributes.addFlashAttribute("products", productService.findAll());
            Order order = orderService.newOrder(Integer.parseInt(id));
                // Order order = new Order(employeeService.findByEmployeeId(Integer.parseInt(id)));
                // orderService.save(order);
            log.warn("order after save " + order);
            Order orderId = orderService.findById(order.getId());
            log.warn("Order id: " + orderId.toString());
            redirectAttributes.addFlashAttribute("order", orderId);
            OrderDetails orderDetails = new OrderDetails();
            redirectAttributes.addFlashAttribute("orderdetails", orderDetails);
            log.warn("Passing order into orderdetails" + orderId);
        } catch (Exception ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("employee_not_found", String.format("Employee: %s not found!", id));
            return "redirect:/orders";
        }
        return "redirect:/orders/gettoneworder";
    }

    @GetMapping("/gettoneworder")
        public String getToNewOrder() {
        return "neworder";
    }

    @PostMapping("/addtoorder/{id}/{prodId}")
    public String addNewOrderDetailsToOrder(RedirectAttributes redirectAttributes,  @ModelAttribute("orderdetails") OrderDetails orderDetails, @PathVariable("id") int id, @PathVariable("prodId") int prodId) {

            orderDetails.setProduct(productService.findById(prodId));
            orderDetails.setOrder(orderService.findById(id));
            orderDetailsService.saveOrUpdate(orderDetails);

            log.warn("Setting up Order in OD: " + orderService.findById(id).toString());
            log.warn("Setting up Product in OD: " + productService.findById(prodId).toString());
            log.warn("Setting up order details for order: " + orderDetails);
            log.warn("Submitting order: " + orderDetails.getOrder());
            log.warn("Product in order details: " + orderDetails.getProduct());
            log.warn("Order details product QUANTITY: " + orderDetails.getQty());

            redirectAttributes.addFlashAttribute("order", orderService.findById(id));
            redirectAttributes.addFlashAttribute("employee", orderService.findById(id).getEmployee());

            // if product was already added to the order - remove from the list
            Set<Product> productsNotInOrder = new HashSet<>(productService.findAll());
            for ( OrderDetails orderD : orderDetailsService.findByOrderId(id)) {
                productsNotInOrder.remove(orderD.getProduct());
            }
            redirectAttributes.addFlashAttribute("products", productsNotInOrder);
            redirectAttributes.addFlashAttribute("orderdetails",  new OrderDetails());

            return "redirect:/orders/gettoneworder";
    }

    @GetMapping ("/submitorder/{id}")
    public String getToFinalOrder(RedirectAttributes redirectAttributes, @PathVariable("id") int id, Model model){
        Order order = orderService.findById(id);
        model.addAttribute("submittedorder", order);
        model.addAttribute("total", order);
        model.addAttribute("orderdetails", orderDetailsService.findByOrderId(id));
        log.warn("OrderDetails found by ID: " + orderDetailsService.findByOrderId(id));
        orderService.placeOrder(order);
        return "completeorder";
    }

}
