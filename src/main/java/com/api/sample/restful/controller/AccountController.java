package com.api.sample.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @GetMapping("/accounts")
    public String list(){
        return "accounts";
    }
}
