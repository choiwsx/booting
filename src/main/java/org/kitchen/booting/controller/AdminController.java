package org.kitchen.booting.controller;

import org.kitchen.booting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public String index() {
        return "/admin/index";
    }

    @GetMapping("user/list")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/admin/user/list";
    }
}
