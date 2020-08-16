package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.userauth.User;


import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.SearchService;
import org.kitchen.booting.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.kitchen.booting.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;



@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int INDEX_RECIPE_COUNT = 12;
    private final int INDEX_FEATURE_COUNT = 3;

    @Autowired
    RecipeService recipeService;
    @Autowired
    TagService tagService;
    @Autowired
    ProfileService profileService;
    @Autowired
    SearchService searchService;

    @GetMapping(value="/")
    public String indexView(Model model)
    {
        String featuredKeyword = "삼계탕";

        List<Recipe> recipes = recipeService.findAll();
        if(recipes.size()>INDEX_RECIPE_COUNT) recipes = recipes.subList(0, INDEX_RECIPE_COUNT-1);
        List<Recipe> features = searchService.searchRecipe(featuredKeyword);
        if(features.size()>INDEX_FEATURE_COUNT) features = features.subList(0, INDEX_FEATURE_COUNT-1);
        model.addAttribute("recipes", recipes);
        model.addAttribute("features", features);
        model.addAttribute("tags",tagService.randomTagList());
        return "index";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public List<String> autoComplete(HttpServletRequest requset){
        List<String> list = tagService.search(requset.getParameter("term"));
        List<String> recipeList = recipeService.search(requset.getParameter("term"));
        List<String> userList = profileService.search(requset.getParameter("term"));
        for (String s : recipeList) {
            list.add(s);
        }
        for (String s: userList) {
            list.add(s);
        }
        return list;
    }

}