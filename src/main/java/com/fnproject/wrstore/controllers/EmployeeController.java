package com.fnproject.wrstore.controllers;


import com.fnproject.wrstore.services.EmployeeService;
import com.fnproject.wrstore.services.OrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "Employees")
public class EmployeeController {

EmployeeService employeeService;
OrderService orderService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, OrderService orderService) {
        this.employeeService = employeeService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getAllEmployees(Model model){
        model.addAttribute("employees",employeeService.findAll());
        return "employees";
    }



}
