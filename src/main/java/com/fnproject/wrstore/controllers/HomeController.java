package com.fnproject.wrstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "index")
public class HomeController {

    @GetMapping//(value = {"/", "index"})
    public String homePage(){
        return "index";
    }
    @GetMapping(value = "/neworder")
    public String newOrder(){
        return "neworder";
    }
}