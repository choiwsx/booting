//package org.kitchen.booting.controller;
//
//import org.kitchen.booting.domain.Recipe;
//import org.kitchen.booting.domain.Step;
//import org.kitchen.booting.service.RecipeService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
////@SessionAttributes("recipe")
//public class RecipeController {
//
//    @Autowired
//    RecipeService recipeService;
//
//    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);
//
//
//    @GetMapping("/recipe/register")
//    public String recipeForm(@ModelAttribute Recipe recipe, Model model){
//        List<Step> steps = new ArrayList<>();
//        Step step1 = new Step();
//        Step step2 = new Step();
//        steps.add(step1);
//        steps.add(step2);
////        List<MaterialVO> materials = new ArrayList<>();
////        MaterialVO material1 = new MaterialVO();
////        MaterialVO material2 = new MaterialVO();
////        materials.add(material1);
////        materials.add(material2);
////        recipe.setMaterials(materials);
//        recipe.setSteps(steps);
//        model.addAttribute("recipeVO", recipe);
//        return "/recipe/register";
//    }
//    @PostMapping("/recipe/register")
//    public String recipeSubmit(@ModelAttribute Recipe recipe, BindingResult bindingResult){
//        System.out.println("@@@@@"+recipe);
//        recipeService.save(recipe);
////        recipe.setMaterials(recipe.getMaterials());
////        recipe.setSteps(recipe.getSteps());
//
////        return "index";
//        return "/recipe/register";
//    }
//
//    @GetMapping("/recipe/list")
//    public String userList(Model model){
//        model.addAttribute("recipes", recipeService.findAll());
//        return "/recipe/list";
//    }
//
//    @GetMapping("/recipe/delete/{recipeNo}")
//    public String delete(@PathVariable("recipeNo") Long recipeNo){
//        recipeService.deleteRecipe(recipeNo);
//        return "redirect:/recipe/list";
//    }
//}
package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Step;
import org.kitchen.booting.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);


    @GetMapping("/recipe/register")
    public String recipeForm(@ModelAttribute Recipe recipe, Model model){
//        model.addAttribute("recipeVO", recipe);
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
        return "/recipe/list";
    }

    @RequestMapping(value="/recipe/{recipeNo}", method = RequestMethod.GET)
    public String get(@PathVariable("recipeNo") Long recipeNo,Model model){
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);
        if(recipe!=null) {
            model.addAttribute("recipe", recipe);
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
}