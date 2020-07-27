package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class JsonController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    RecipeService recipeService;

    @Autowired
    public void setRecipeService(RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }


    @PostMapping("/recipe/ajaxTest")
    public void createRecipe(@RequestBody Recipe recipe)
    {
        logger.info("@@@"+recipe);
        recipeService.save(recipe);
    }


    @PostMapping("/recipe/updateTest")
    public void updateRecipe(@RequestBody Recipe recipe)
    {
//        logger.info("@@@@@"+recipe.getTitle());
//        recipe.setTitle(recipe.getTitle());
//        logger.info("@@@@@@"+recipe.getSteps()+"");
//        recipe.setSteps();
        logger.info(recipe+"");

//        if(recipe!=null) {
//            logger.info("@"+recipe.getSteps());
            recipeService.save(recipe);
//        }
    }

}