package org.kitchen.booting.controller;


import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.service.ProfileService;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    SearchService searchService;
    @Autowired
    RecipeService recipeService;
    @Autowired
    ProfileService profileService;

    @GetMapping("/")
    public String home(Model model){

        List<Recipe> recipeList = recipeService.findAll();
        List<Profile> profileList = profileService.findAll();
        model.addAttribute("recipes", recipeList);
        model.addAttribute("profiles", profileList);
        return "index";
    }

}
