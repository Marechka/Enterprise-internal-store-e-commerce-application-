package com.fnproject.wrstore.controllers;

import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.Product;
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

import java.sql.SQLIntegrityConstraintViolationException;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "products")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model){
        model.addAttribute("products",productService.findAll());
        return "products";
    }
    @PostMapping("/findproductbyname")
    public RedirectView findProductByName(@RequestParam(required = false) String name, RedirectAttributes redirectAttributes){
        log.warn("product name: " + name);
        try {
            redirectAttributes.addFlashAttribute("foundproduct", productService.findProductByName(name));
        } catch (RuntimeException ex){
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("product_not_found",String.format("Product: %s not found!",name));
            return new RedirectView("/products");
        }
        return new RedirectView("/products");
    }

    @GetMapping(value="/productform")
    public String studentForm(Model model){
        model.addAttribute("product",new Product());
        return "editcreateproduct";
    }
    @PostMapping("/saveupdateproduct")
    public String saveUpdateEmployee(RedirectAttributes model, @ModelAttribute("product") Product product){
        log.warn("Model product: "+ product);
        productService.saveOrUpdate(product);
        model.addFlashAttribute("product",productService.findById(product.getId()));
        //model.addFlashAttribute("addedEmpId", employee.getId());
        return "redirect:/products";
    }

    @GetMapping("/deleteproduct/{id}")
    public RedirectView deleteEmployee(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.findById(id);
            productService.delete(product);
            log.warn("Model to delete: " + product);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("product_is_used_in_order",String.format("Product: id# %s cannot be deleted (part of purchase history)!", id));
            return new RedirectView("/products");
        }
        return new RedirectView("/products");
    }


}