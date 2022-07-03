package com.fnproject.wrstore.controllers;


import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.services.EmployeeService;
import com.fnproject.wrstore.services.OrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashSet;
import java.util.Set;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "employees")
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

    @PostMapping("/findemployeebyid")
    public RedirectView findEmployeeById(@RequestParam(required = false) int id, RedirectAttributes redirectAttributes){
        log.warn("employee id: " + id);
        try {
            redirectAttributes.addFlashAttribute("employee", employeeService.findByEmployeeId(id));
        } catch (RuntimeException ex){
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("employee_not_found",String.format("Employee: %s not found!",id));
            return new RedirectView("/employees");
        }
        return new RedirectView("/employees");
    }

    @GetMapping(value="/employeeform")
    public String studentForm(Model model){
        model.addAttribute("employee",new Employee());
        return "editcreateemployee";
    }
    @PostMapping("/saveupdateemployee")
    public String saveUpdateEmployee(RedirectAttributes model, @ModelAttribute("employee") Employee employee){
        log.warn("Model employee: "+ employee);
        employeeService.saveOrUpdate(employee);
        model.addFlashAttribute("employee",employeeService.findByEmployeeId(employee.getId()));
        //model.addFlashAttribute("addedEmpId", employee.getId());
        return "redirect:/employees";
    }

    @GetMapping("/deleteemployee/{id}")
    public String deleteEmployee(@PathVariable("id") int id, RedirectAttributes model) {
        Employee emp = employeeService.findByEmployeeId(id);
        log.warn("Model to delete: " + emp);
        employeeService.delete(emp);
        return "redirect:/employees";
    }





}
