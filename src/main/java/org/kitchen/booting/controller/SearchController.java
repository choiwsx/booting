package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    private final Logger logger = LoggerFactory.getLogger(SearchController.class);
    public SearchController(SearchService searchService) { this.searchService = searchService; }

    @GetMapping("/list")
    public String search(@RequestParam(value="keyword") String keyword, Model model
            ,@RequestParam(value="page", defaultValue = "1") Integer pageNum)
    {
        List<Recipe> recipeList = searchService.getRecipeList(keyword, pageNum);
        List<Profile> profileList = searchService.getProfileList(keyword, pageNum);
        List<Recipe> tagRecipeList = searchService.getTagList(keyword, pageNum);
//        List<Recipe> tagRecipeList = searchService.searchTag(keyword);

        model.addAttribute("allRecipe", searchService.searchRecipe(keyword));
        model.addAttribute("allProfile",  searchService.searchProfile(keyword));
        model.addAttribute("allTag",  searchService.searchTag(keyword));
        model.addAttribute("recipes", recipeList);
        model.addAttribute("profiles", profileList);
        model.addAttribute("tagRecipe", tagRecipeList);
        model.addAttribute("keyword", keyword);
        return "/list";
    }

    @GetMapping("/recipelist")
    public String searchRecipe(@RequestParam(value="keyword") String keyword, Model model
            ,@RequestParam(value="page", defaultValue = "1") Integer pageNum)
    {
        List<Recipe> recipeList = searchService.getRecipeList(keyword, pageNum);
        Integer[] pageList = searchService.getRecipePageList(keyword, pageNum);

        model.addAttribute("recipes", recipeList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("keyword", keyword);
        return "/recipelist";
    }

    @GetMapping("/userlist")
    public String searchUser(@RequestParam(value="keyword") String keyword, Model model
            ,@RequestParam(value="page", defaultValue = "1") Integer pageNum)
    {
        List<Profile> profileList = searchService.getProfileList(keyword, pageNum);
        Integer[] pageList = searchService.getProfilePageList(keyword, pageNum);

        model.addAttribute("profiles", profileList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("keyword", keyword);
        return "/userlist";
    }

    @GetMapping("/taglist")
    public String searchTag(@RequestParam(value="keyword") String keyword, Model model
            ,@RequestParam(value="page", defaultValue = "1") Integer pageNum)
    {
        List<Recipe> tagList = searchService.getTagList(keyword, pageNum);
        Integer[] pageList = searchService.getTagPageList(keyword, pageNum);

        model.addAttribute("tagRecipe", tagList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("keyword", keyword);
        return "/taglist";
    }

    @RequestMapping(value = "searchlist", method = RequestMethod.POST)
    public List<Recipe> searchAutocomplete(@RequestParam("keyword") String keyword){
//       List<Recipe> recipes =
//           List<String> userList = new ArrayList<>();
//       for(Recipe i : recipes){
//           userList.add(i.getContent());
//       }
    return searchService.searchRecipe(keyword);
    }
}
