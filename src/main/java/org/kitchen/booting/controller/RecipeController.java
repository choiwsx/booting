package org.kitchen.booting.controller;

import org.kitchen.booting.domain.*;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.CategoryRepository;
import org.kitchen.booting.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Autowired
    ProfileService profileService;
    @Autowired
    CategoryRepository categoryRepository;

    CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);


    @GetMapping("/recipe/register")
    public String recipeForm(@ModelAttribute Recipe recipe, Model model){
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("category", categoryList);
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
        return "recipe/list";
    }

    @RequestMapping(value="/recipe/{recipeNo}", method = RequestMethod.GET)
    public String get(@AuthenticationPrincipal User user,
                      @PathVariable("recipeNo") Long recipeNo,Model model) {
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);
        Profile profile = profileService.findByUserId(recipe.getProfile().getUserId());
        recipe.getSteps().sort((a, b)-> a.getStepNo().compareTo(b.getStepNo()));
        recipe.getIngredients().sort((a,b)->a.getIngredientNo().compareTo(b.getIngredientNo()));
//        Scrap scrap = scrapService.getScrap(user.getUserId(), recipeNo);
//        Like like = likeService.getLike(user.getUserId(), recipeNo);
//        List<Profile> count = likeService.listByRecipeNo(recipeNo);
        List<String> recipeTag = recipeService.CheckTag(recipeNo);
        if (recipe != null) {
//            if (count == null || recipeService.CheckTag(recipeNo) == null) {
            model.addAttribute("recipe", recipe);
            model.addAttribute("profile",profile);
//                model.addAttribute("scrap", scrap);
//                model.addAttribute("like", like);
            model.addAttribute("recipeTag", recipeTag); //레시피 태그
//                model.addAttribute("allLike", likeService.findAll());
//            model.addAttribute("counts", count);
        } else {
            model.addAttribute("recipe", recipe);
            model.addAttribute("profile",profile);
            model.addAttribute("recipeTag", recipeService.CheckTag(recipeNo));
//                model.addAttribute("scrap", scrap);
//                model.addAttribute("like", like);
            model.addAttribute("recipeTag", recipeTag); //레시피 태그
//                model.addAttribute("allLike", likeService.findAll());
//                model.addAttribute("counts", count);
//            }
        }
        return "recipe/get_";
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

    @RequestMapping(value="/recipe/getSubCategories", method=RequestMethod.GET)
    public @ResponseBody List<Category> findSubCategory(
            @RequestParam(value ="prevCateNo", required = true) Long prevCateNo){
        Optional<Category> mainCategory = categoryRepository.findById(prevCateNo);
        List<Category> allCategory =  new ArrayList<>();
        List<Category> subCategories = new ArrayList<>();
        if(mainCategory.isPresent())
        {
            allCategory = categoryRepository.findAll();
            for(int i=0; i<allCategory.size(); i++)
            {
                if(allCategory.get(i).getMainCategory()!=null) {
                    if(allCategory.get(i).getMainCategory().getCategoryNo().equals(prevCateNo))
                    {
                        logger.info("!!");
                        subCategories.add(allCategory.get(i));

                    }
                }
            }
            logger.info("@@@서브카테고리"+subCategories);
        }
        subCategories.forEach(sub->sub.setSubCategories(null));
        subCategories.forEach(sub->sub.getMainCategory().setSubCategories(null));
        subCategories.forEach(sub->sub.getMainCategory().setRecipes(null));
        subCategories.forEach(sub->sub.setRecipes(null));

        return subCategories;
    }

}