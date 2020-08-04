package org.kitchen.booting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @GetMapping("")
    public String index() {
        return "/admin/index";
    }

    @GetMapping("user/list")
    public String userList() {
        return "/admin/user/list";
    }
}
