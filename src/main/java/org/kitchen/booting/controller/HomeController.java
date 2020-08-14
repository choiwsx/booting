package org.kitchen.booting.controller;

import org.kitchen.booting.domain.AutoCompleteDTO;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.userauth.User;


import org.kitchen.booting.repository.RecipeRepository;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;



@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RecipeService recipeService;
    @Autowired
    TagService tagService;
    @Autowired
    ProfileService profileService;
    @Autowired
    SearchService searchService;
    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping(value="/")
    public String indexView(@AuthenticationPrincipal User user, Model model)
    {
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);
//        logger.info("@@@@"+recipes.get(0).getTags().size());
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

    @ResponseBody
    @RequestMapping(value = "searchList", method = RequestMethod.POST)
    public List<Recipe> searchAutocomplete(@RequestParam("keyword") String keyword){

//        List<AutoCompleteDTO> list2 = searchService.searchAuto(keyword);
        List<Recipe> list3 = recipeRepository.findByTitleContaining(keyword);
        List<AutoCompleteDTO> list4 = new ArrayList<>();
        if(list3.size()>5)
        {
            list3.subList(0,4);
        }
        list3.forEach(recipe->list4);
        return list4;
    }

}