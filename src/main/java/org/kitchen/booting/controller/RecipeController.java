package org.kitchen.booting.controller;

import org.kitchen.booting.domain.*;
import org.kitchen.booting.service.LikeService;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.ScrapService;
import org.kitchen.booting.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
//@SessionAttributes("recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;
    @Autowired
    TagService tagService;
    @Autowired
    ScrapService scrapService;
    @Autowired
    LikeService likeService;

    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);


    @GetMapping("/recipe/register")
    public String recipeForm(@ModelAttribute Recipe recipe){
//        model.addAttribute("recipe", recipe);
        return "/recipe/register";
    }
//    @PostMapping("/recipe/register")
//    public String recipeSubmit(@ModelAttribute Recipe recipe, BindingResult bindingResult){
//        System.out.println("@@@@@"+recipe);
//        return "/recipe/register";
//    }

    @GetMapping("/recipe/list")
    public String userList(Model model){
        model.addAttribute("recipes", recipeService.findAll());
        model.addAttribute("tags",tagService.randomTagList());
        return "/recipe/list";
    }

    @RequestMapping(value="/recipe/{recipeNo}", method = RequestMethod.GET)
    public String get(@PathVariable("recipeNo") Long recipeNo,Model model){
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);
        Scrap scrap = scrapService.getScrap("user01", recipeNo);
        Like like = likeService.getLike("user01", recipeNo);
        List<Profile> count = likeService.listByRecipeNo(recipeNo);
        if(recipe!=null) {
            if(count == null || recipeService.CheckTag(recipeNo) == null)
            {
                model.addAttribute("recipe", recipe);
                model.addAttribute("scrap", scrap);
                model.addAttribute("like", like);
//                model.addAttribute("allLike", likeService.findAll());
                model.addAttribute("counts", count);
            }
            else
            {
                model.addAttribute("recipe", recipe);
                model.addAttribute("recipeTag", recipeService.CheckTag(recipeNo));
                model.addAttribute("scrap", scrap);
                model.addAttribute("like", like);
//                model.addAttribute("allLike", likeService.findAll());
                model.addAttribute("counts", count);
            }
        }
        return "recipe/get";
    }

    @RequestMapping(value="/recipe/modify/{recipeNo}",method = RequestMethod.GET)
    public String modify(@PathVariable("recipeNo") Long recipeNo, Model model){
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);
        if(recipe!=null)
        {
            model.addAttribute("recipe", recipe);
        }
        return "recipe/modify";
    }

    @GetMapping("/recipe/delete/{recipeNo}")
    public String delete(@PathVariable("recipeNo") Long recipeNo){
        recipeService.deleteRecipe(recipeNo);
        return "redirect:/recipe/list";
    }

    @GetMapping("/recipe/tag")
    public String tagForm(@ModelAttribute Tag tag){
        return "/recipe/tag";
    }

    @PostMapping("/recipe/tag")
    public String tagSubmit(@ModelAttribute Tag tag){
//        tagService.save(tag);
        return "/index";
    }
    
    @GetMapping("/recipe/likelist")
    public String likeList(Long recipeNo, Model model) {
        model.addAttribute("profiles", likeService.listByRecipeNo(recipeNo));
        model.addAttribute("title", recipeService.findByRecipeNo(recipeNo).getTitle());
        return "/recipe/likelist";
    }
}