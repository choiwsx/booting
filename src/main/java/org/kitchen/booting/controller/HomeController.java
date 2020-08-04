package org.kitchen.booting.controller;

import org.kitchen.booting.domain.userauth.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value="/")
    public String indexView(@AuthenticationPrincipal User user)
    {

        return "index";
    }
}
