package org.kitchen.booting.controller;

import org.kitchen.booting.domain.*;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.CategoryRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.kitchen.booting.service.LikeService;
import org.kitchen.booting.service.CommentService;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.ScrapService;
import org.kitchen.booting.service.TagService;
import org.kitchen.booting.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import java.util.*;

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

    @Autowired
    RecipeRepository recipeRepository;

    CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);


    @GetMapping("recipe/register")
    public String recipeForm(@ModelAttribute Recipe recipe, Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("category", categoryList);
        return "recipe/register";
    }
//    @PostMapping("/recipe/register")
//    public String recipeSubmit(@ModelAttribute Recipe recipe, BindingResult bindingResult){
//        System.out.println("@@@@@"+recipe);
//        return "/recipe/register";
//    }

    @GetMapping("recipe/list")
    public String recipeList(@RequestParam(value="page", defaultValue = "1") Integer pageNum,Model model) {

        List<Recipe> recipe = recipeService.findAll();
//        Integer[] pageList = recipeService.recipePageList(pageNum);
//        Integer lastPage = recipeService.getLastPage(pageNum);
        model.addAttribute("recipes", recipe);
//        model.addAttribute("curPage", pageNum);
//        model.addAttribute("lastPage", lastPage);
//        model.addAttribute("pageList", pageList);
        return "recipe/picgridlist";
    }

    @GetMapping("recipe/recent")
    public String sortRecipe(Model model){
//        model.addAttribute("re")
//        Sort sort = new Sort(Sort.Direction.DESC, "regDate");
        List<Recipe> sortRecipe = recipeRepository.findAllByOrderByUpDate();
        model.addAttribute("recipes", sortRecipe);
//        model.addAttribute("tags", tagService.randomTagList());
        sortRecipe.forEach(s->s.getRecipeNo().toString());

//        logger.info(sortRecipe.toString());


        return "recipe/list";
    }

    @GetMapping("recipe/category/{categoryNo}")
    public String recipeByCategory(Model model,@PathVariable("categoryNo") Long categoryNo,
    @RequestParam(value="page", defaultValue = "1") Integer pageNum){
        Integer[] pageList = recipeService.recipePageList(pageNum);
        Optional<Category> cate = categoryRepository.findById(categoryNo);
        if(cate.isPresent()) {
            Category category = cate.get();
            if (category.getMainCategory() != null) {
                List<Recipe> recipes = recipeRepository.findByCategoryOrderByRecipeNo(category);
                model.addAttribute("recipes", recipes);
            } else {
                List<Category> categories = categoryRepository.findByMainCategory(category);

                List<Recipe> recipes = new ArrayList<>();
                categories.forEach(c -> recipes.addAll(recipeRepository.findByCategoryOrderByRecipeNo(c)));
                model.addAttribute("recipes", recipes);
            }
        } else {
            model.addAttribute("recipes", null);
        }
//        model.addAttribute("pageList", pageList);


        return "recipe/picgridlist";
    }

    @RequestMapping(value = "recipe/{recipeNo}", method = RequestMethod.GET)
    public String get(@AuthenticationPrincipal User user,
                      @PathVariable("recipeNo") Long recipeNo, Model model) {
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);
        if(user!=null)
        {
            model.addAttribute("scrap", scrapService.getScrap(user.getUserId(), recipeNo));
            model.addAttribute("mylike", likeService.getLike(user.getUserId(), recipeNo));
        }
        else { model.addAttribute("mylike", null); }

        model.addAttribute("like", likeService.list(recipeNo));
        Profile profile = profileService.findByUserId(recipe.getProfile().getUserId());
        recipe.getSteps().sort((a, b) -> a.getStepNo().compareTo(b.getStepNo()));
        recipe.getIngredients().sort((a, b) -> a.getIngredientNo().compareTo(b.getIngredientNo()));
        List<String> recipeTag = recipeService.CheckTag(recipeNo);
        model.addAttribute("recipe", recipe);
        model.addAttribute("profile",profile);
        model.addAttribute("follower", profileService.realFollower(profile.getUserId()));

        model.addAttribute("recipeTag", recipeTag); //레시피 태그
        return "recipe/get";
    }

    @RequestMapping(value = "recipe/modify/{recipeNo}", method = RequestMethod.GET)
    public String modify(@PathVariable("recipeNo") Long recipeNo, Model model) {
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);
        if (recipe != null) {
            model.addAttribute("recipe", recipe);
            List<Category> categoryList = categoryRepository.findAll();
            model.addAttribute("category", categoryList);
        }
        return "recipe/modify";
    }

    @GetMapping("recipe/delete/{recipeNo}")
    public String delete(@PathVariable("recipeNo") Long recipeNo, @AuthenticationPrincipal User user) {
        recipeService.deleteRecipe(recipeNo);
        if(user==null)
        {
            return "redirect:/admin/recipe/list";
        }
        return "redirect:/recipe/list";
    }

    @GetMapping("recipe/tag")
    public String tagForm(@ModelAttribute Tag tag) {
        return "recipe/tag";
    }

    @PostMapping("recipe/tag")
    public String tagSubmit(@ModelAttribute Tag tag) {
//        tagService.save(tag);
        return "index";
    }

    @GetMapping("recipe/likelist")
    public String likeList(Long recipeNo, Model model) {
        model.addAttribute("profiles", likeService.listByRecipeNo(recipeNo));
        model.addAttribute("title", recipeService.findByRecipeNo(recipeNo).getTitle());
        return "recipe/likelist";
    }

    @RequestMapping(value = "recipe/getSubCategories", method = RequestMethod.GET)
    public @ResponseBody
    List<Category> findSubCategory(
            @RequestParam(value = "prevCateNo", required = true) Long prevCateNo) {
        Optional<Category> mainCategory = categoryRepository.findById(prevCateNo);
        List<Category> allCategory = new ArrayList<>();
        List<Category> subCategories = new ArrayList<>();
        if (mainCategory.isPresent()) {
            allCategory = categoryRepository.findAll();
            for (int i = 0; i < allCategory.size(); i++) {
                if (allCategory.get(i).getMainCategory() != null) {
                    if (allCategory.get(i).getMainCategory().getCategoryNo().equals(prevCateNo)) {
                        logger.info("!!");
                        subCategories.add(allCategory.get(i));

                    }
                }
            }
            logger.info("@@@서브카테고리" + subCategories);
        }
        subCategories.forEach(sub -> sub.setSubCategories(null));
        subCategories.forEach(sub -> sub.getMainCategory().setSubCategories(null));
        subCategories.forEach(sub -> sub.getMainCategory().setRecipes(null));
        subCategories.forEach(sub -> sub.setRecipes(null));

        return subCategories;
    }

//    @GetMapping("recipe/category/{categoryNo}")
//    public String getRecipeByCategory(@PathVariable("categoryNo") Long categoryNo, Model model)
//    {
//        Optional<Category> CategoryNo = categoryRepository.findById(categoryNo);
//        if(CategoryNo.isPresent())
//        {
//            Set<Recipe> recipeList = new HashSet<>();
//            Category mainCategory = CategoryNo.get().getMainCategory();
//
//            //매개변수로 받은 카테고리 번호로 레시피 찾기
//            List<Recipe> getByCategoryNo = recipeService.findByCategoryNo(CategoryNo.get());
//            if(getByCategoryNo!=null){
//                getByCategoryNo.forEach(recipe->recipeList.add(recipe));
//            }
//            //매개변수로 받은 카테고리의 메인 카테고리 레시피도 찾기
//            List<Recipe> getByMainCategoryNo = new ArrayList<>();
//            getByMainCategoryNo = recipeService.findByCategoryNo(mainCategory);
//            if(mainCategory!=null)
//            {
//                getByMainCategoryNo.forEach(recipe->recipeList.add(recipe));
//            }
//            model.addAttribute("recipes", recipeList);
//        }
//        return "recipe/getCategory";
//
//    }

    @GetMapping("recipe/followeelist")
    public String getFolloweeList(@AuthenticationPrincipal User user,@RequestParam(value="page", defaultValue = "1") Integer pageNum, Model model)
    {
        List<Recipe> list = new ArrayList<>();
        List<Profile> followList = profileService.realFollowee(user.getUserId());
        List<String> userlist = new ArrayList<>();
        followList.forEach(e->userlist.add(e.getUserId()));

        for(int i=0; i<userlist.size(); i++)
        {
            List<Recipe> recipes = recipeService.findByUserId(userlist.get(i));
            list.addAll(recipes);
        }

        model.addAttribute("recipes",list);
        return "recipe/picgridlist";
    }

//    @GetMapping("recipe/category/{categoryNo}")
//    public String getRecipeByCategoryWithPage(@RequestParam(value="page", defaultValue = "1") Integer pageNum, @PathVariable("categoryNo") Long categoryNo, Model model)
//    {
//        Optional<Category> CategoryNo = categoryRepository.findById(categoryNo);
//        if(CategoryNo.isPresent())
//        {
//            Set<Recipe> recipeList = new HashSet<>();
//            Category mainCategory = CategoryNo.get().getMainCategory();
//
//            //매개변수로 받은 카테고리 번호로 레시피 찾기
//            List<Recipe> getByCategoryNo = recipeService.findByCategoryNo(CategoryNo.get());
//            if(getByCategoryNo!=null){
//                getByCategoryNo.forEach(recipe->recipeList.add(recipe));
//            }
//            //매개변수로 받은 카테고리의 메인 카테고리 레시피도 찾기
//            List<Recipe> getByMainCategoryNo = new ArrayList<>();
//            getByMainCategoryNo = recipeService.findByCategoryNo(mainCategory);
//            if(mainCategory!=null)
//            {
//                getByMainCategoryNo.forEach(recipe->recipeList.add(recipe));
//            }
//            model.addAttribute("recipes", recipeList);
//        }
//        return "recipe/getCategory";
//
//    }

}
