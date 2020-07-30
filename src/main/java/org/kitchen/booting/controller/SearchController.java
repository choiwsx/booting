package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;


    public SearchController(SearchService searchService)
    {
        this.searchService = searchService;
    }

    @GetMapping("/list")
    public String search(@RequestParam(value="keyword") String keyword, Model model)
    {
        List<Recipe> recipeList = searchService.searchRecipe(keyword);
        List<Profile> profileList = searchService.searchProfile(keyword);
        model.addAttribute("recipes", recipeList);
        model.addAttribute("profiles", profileList);
        return "/list";
    }

}
