package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class JsonController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RecipeService recipeService;

    @Autowired
    public void setRecipeService(RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }

//recipe CRUD

    @PostMapping("/recipe/ajaxTest")
    public ResponseEntity<Void> createOrUpdateRecipe(@RequestBody Recipe recipe) {
        recipeService.save(recipe);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @RequestMapping(value = "/recipe/ajaxUpdate", method = RequestMethod.POST, headers="Content-Type=application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public void updateRecipe(@RequestBody Recipe recipe)
//    {
////        logger.info("@@@@@"+recipe.getTitle());
////        recipe.setTitle(recipe.getTitle());
////        logger.info("@@@@@@"+recipe.getSteps()+"");
////        recipe.setSteps();
//        logger.info(recipe+"");
//
////        if(recipe!=null) {
////            logger.info("@"+recipe.getSteps());
////            recipeService.save(recipe);
////        }
//    }

    @DeleteMapping("/recipe/ajax/delete/{recipeNo}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeNo) {
        recipeService.deleteRecipe(recipeNo);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}