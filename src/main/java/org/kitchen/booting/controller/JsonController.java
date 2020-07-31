package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Scrap;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.ScrapService;
import org.kitchen.booting.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JsonController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    RecipeService recipeService;
    ScrapService scrapService;

    @Autowired
    TagService tagService;

    @Autowired
    public void setScrapService(ScrapService scrapService) { this.scrapService = scrapService; }
    @Autowired
    public void setRecipeService(RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }


    @PostMapping("/recipe/ajaxTest")
    public void createRecipe(@RequestBody Recipe recipe)
    {
        logger.info("@@@"+recipe);

        tagService.insert(recipe);
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

    @PostMapping("/recipe/saveScrapAjax")
    public void createScrap(@RequestBody Scrap scrap) {
        logger.info("스크랩하기 >>>>>>>>>>>>>>>>> 되나요?");
        String userId = scrap.getUserId();
        Long recipeNo = scrap.getRecipeNo();
        // userId로 scrapList 찾아서 이미 있는 recipeNo이면 return
        if(scrapService.getScrap(userId, recipeNo) != null) {
            logger.info("이미 있어서 저장안됨~ 하하하!");
            return;
        }
        // session에 userId가 없거나 recipeNo가 없으면 return
        if(userId == null || recipeNo == null) { return; }
//        logger.info(scrap.toString());
        scrapService.save(scrap);
    }

    @PostMapping("/recipe/deleteScrapAjax")
    public void deleteScrap(@RequestBody Scrap scrap) {
        logger.info("스크랩취소 >>>>>>>>>>>>>>>>> 되나요?");
        String userId = scrap.getUserId();
        Long recipeNo = scrap.getRecipeNo();
        // 찾아봤는데 어차피 없으면 삭제안됨
        if(scrapService.getScrap(userId, recipeNo) == null) {
            logger.info("애초에 없어서 취소안됨~ 하하하!");
            return;
        }
        // session에 userId가 없거나 recipeNo가 없으면 return
        if(userId == null || recipeNo == null) { return; }
        scrapService.delete(userId, recipeNo);
    }

    @GetMapping(value = "recipe/goScrap/{userId}/{recipeNo}")
    public ResponseEntity<?> goScrap(@PathVariable String userId, @PathVariable Long recipeNo) {
        Scrap scrap = scrapService.getScrap(userId, recipeNo);
//        if(scrap == null ) {
//
//        }
//        logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+scrap);
        return ResponseEntity.status(HttpStatus.OK).body(scrap == null ? "empty" : scrap);
    }

}