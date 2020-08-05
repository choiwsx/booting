package org.kitchen.booting.controller;

import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    RecipeService recipeService;
    @Autowired
    TagService tagService;

    @GetMapping(value="/")
    public String indexView(@AuthenticationPrincipal User user, Model model)
    {
        model.addAttribute("recipes", recipeService.findAll());
        model.addAttribute("tags",tagService.randomTagList());
        return "index";
    }
}
