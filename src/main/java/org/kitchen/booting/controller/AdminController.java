package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Category;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Report;
import org.kitchen.booting.domain.Tag;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.CategoryRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.kitchen.booting.repository.TagRepository;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.ReportService;
import org.kitchen.booting.service.TagService;
import org.kitchen.booting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagService tagService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ReportService reportService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public String index() {
        return "admin/index";
    }

    @GetMapping("login")
    public String login() {
        return "admin/login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user/list")
    public String userList(Model model, @RequestParam(value="page", defaultValue = "1")Integer pageNum) {
        List<User> userList = userService.getUserList(pageNum);
        Integer[] pageList = userService.getPageList(pageNum);
        model.addAttribute("users", userList);
        model.addAttribute("cur_page", pageNum);
        model.addAttribute("pageList", pageList);
        return "admin/user/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("recipe/list")
    public String getRecipeList(Model model, @RequestParam(value="page", defaultValue = "1")Integer pageNum)
    {
        List<Recipe> recipes = recipeService.recipeList(pageNum);
        Integer[] pageList = recipeService.recipePageList(pageNum);
        model.addAttribute("recipes", recipes);
        model.addAttribute("cur_page", pageNum);
        model.addAttribute("pageList", pageList);
        return "admin/recipe/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("tag/list")
    public String getTagList(Model model, @RequestParam(value="page", defaultValue = "1")Integer pageNum){
        List<Tag> tags = tagService.getTagList(pageNum);
        Integer[] pageList = tagService.getPageList(pageNum);
        model.addAttribute("tags",tags);
        model.addAttribute("cur_page", pageNum);
        model.addAttribute("pageList", pageList);
        return "admin/tag/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("report/list")
    public String getReportList(Model model, @RequestParam(value="page", defaultValue = "1")Integer pageNum){
        List<Report> reports = reportService.getReportList(pageNum);
        Integer[] pageList = tagService.getPageList(pageNum);
        model.addAttribute("reports",reports);
        model.addAttribute("cur_page", pageNum);
        model.addAttribute("pageList", pageList);
        return "admin/report/list";
    }





    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user/get/{userId}")
    public String getUser(@PathVariable("userId") String userId,Model model)
    {
        User user = userService.findByUserId(userId);
        model.addAttribute("getUser", user);
        return "/admin/user/get";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user/edit/{userId}")
    public String editUser(@PathVariable("userId") String userId, Model model)
    {
        User user = userService.findByUserId(userId);
        model.addAttribute("user", user);
        return "/admin/user/edit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("category/list")
    public String categoryList(Model model)
    {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("category", categoryList);
        logger.info("@@카테고리리스트@@"+categoryList.toString());
        return "/admin/category/list";
    }
    @GetMapping("category/create")
    public String category(Model model)
    {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("category", categoryList);
        return "/admin/category/create";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("category/edit")
    public String editCategory(Model model)
    {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("category", categoryList);
        model.addAttribute("categories", categoryList);
        return "/admin/category/edit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("category/get/{categoryNo}")
    public String getRecipeByCategory(@PathVariable("categoryNo") Long categoryNo, Model model)
    {
        Optional<Category> CategoryNo = categoryRepository.findById(categoryNo);
        if(CategoryNo.isPresent())
        {
            Set<Recipe> recipeList = new HashSet<>();
            Category mainCategory = CategoryNo.get().getMainCategory();

            //매개변수로 받은 카테고리 번호로 레시피 찾기
            List<Recipe> getByCategoryNo = recipeService.findByCategoryNo(CategoryNo.get());
            if(getByCategoryNo!=null){
                getByCategoryNo.forEach(recipe->recipeList.add(recipe));
            }
            //매개변수로 받은 카테고리의 메인 카테고리 레시피도 찾기
            List<Recipe> getByMainCategoryNo = new ArrayList<>();
            getByMainCategoryNo = recipeService.findByCategoryNo(mainCategory);
            if(mainCategory!=null)
            {
                getByMainCategoryNo.forEach(recipe->recipeList.add(recipe));
            }
            model.addAttribute("recipes", recipeList);
        }
        return "/admin/category/get";

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("recipe/{recipeNo}")
    public String getRecipe(@PathVariable("recipeNo") Long recipeNo, Model model)
    {
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);
        if(recipe!=null)
            model.addAttribute("recipe", recipe);
        return "/admin/recipe/get";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("recipe/edit/{recipeNo}")
    public String editRecipe(@PathVariable("recipeNo") Long recipeNo, Model model)
    {
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);
        if(recipe!=null) {
            model.addAttribute("recipe", recipe);
            List<Category> categoryList = categoryRepository.findAll();
            model.addAttribute("category", categoryList);
        }
        return "/admin/recipe/edit";
    }

    //태그


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("tag/delete/{tagNo}")
    public String deleteTag(@PathVariable("tagNo") Long tagNo){
        tagService.delete(tagNo);
        return "redirect:/admin/tag/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("tag/get/{tagNo}")
    public String getRecipeByTag(@PathVariable("tagNo") Long tagNo, Model model)
    {
        List<Long> includeTag = tagRepository.findRecipeNoByTagNo(tagNo);
        List<Recipe> getByTagNo = new ArrayList<>();
        if((includeTag.isEmpty())==false){
            for (Long recipeNo:includeTag) {
                getByTagNo.add(recipeService.findByRecipeNo(recipeNo));
            }
        }
        model.addAttribute("recipes", getByTagNo);
        return "/admin/tag/get";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("tag/create")
    public String saveTag(String keyword) {
        Tag tag = new Tag();
        String content = keyword.replaceAll(" ","");
        content = keyword.replaceAll("\\p{Z}","");
        tag.setContent(content);
        if (tagRepository.findByContent(tag.getContent()) != null) {
            Tag oldTag = tagRepository.findByContent(tag.getContent());
            tagRepository.saveAndFlush(oldTag);
        } else {
            tagRepository.saveAndFlush(tag);
        }
        return "redirect:/admin/tag/list";
    }
}