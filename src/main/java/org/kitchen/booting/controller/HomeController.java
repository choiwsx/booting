package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RecipeService recipeService;
    @Autowired
    TagService tagService;

    @GetMapping(value="/")
    public String indexView(@AuthenticationPrincipal User user, Model model)
    {
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);
        logger.info("@@@@"+recipes.get(0).getTags().size());
        model.addAttribute("tags",tagService.randomTagList());
        return "index";
    }
}
