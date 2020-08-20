package org.kitchen.booting.controller;

import org.kitchen.booting.domain.*;
import org.kitchen.booting.domain.userauth.User;


import org.kitchen.booting.repository.ProfileRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.kitchen.booting.repository.TagRepository;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.SearchService;
import org.kitchen.booting.service.TagService;
import org.kitchen.booting.special.Special;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.kitchen.booting.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int INDEX_RECIPE_COUNT = 12;
    private final int INDEX_FEATURE_COUNT = 3;
    private int no = 0;

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

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    Special special;


    @GetMapping(value="/")
    public String indexView(@AuthenticationPrincipal User user, Model model)
    {
        String featuredKeyword = "추석";

        List<Recipe> recipes = recipeService.findAll();
        if(recipes.size()>INDEX_RECIPE_COUNT) recipes = recipes.subList(0, INDEX_RECIPE_COUNT-1);
        List<Recipe> features = searchService.searchRecipe(featuredKeyword);
        if(features.size()>INDEX_FEATURE_COUNT) features = features.subList(0, INDEX_FEATURE_COUNT-1);
        model.addAttribute("recipes", recipes);
//        logger.info("@@@@"+recipes.get(0).getTags().size());
        model.addAttribute("features", features);
        model.addAttribute("tags",tagService.randomTagList());
//        model.addAttribute("popularTag", recipeService.getPopularRecipeByTag().subList(0,5));
        return "index";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public List<String> autoComplete(HttpServletRequest requset){
        List<String> list = tagService.search(requset.getParameter("term"));
//        List<String> recipeList = recipeService.search(requset.getParameter("term"));
        List<String> userList = profileService.search(requset.getParameter("term"));
//        for (String s : recipeList) {
//            list.add(s);
//        }
        for (String s: userList) {
            list.add(s);
        }
        return list;
    }

    //    @RequestMapping(value="popularTag", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Recipe> getPopularRecipeByTag()
//    {
//        List<Recipe> allRecipe = recipeService.findAll();
//        HashMap<Long, Integer> tagMap = new HashMap<>();
////        allRecipe.forEach(r->r.getTags().size()>0?
////                tagMap.put(1,1):);
//        for(int i=0; i<allRecipe.size(); i++)
//        {
//            //태그가 있으면.
//            if(allRecipe.get(i).getTags().size()>0)
//            {
//                allRecipe.get(i).getTags().forEach(t->tagMap.containsKey(t.getTagNo()) ? tagMap.put(t.getTagNo(), tagMap.get(t.getTagNo())+1) :  tagMap.put(t.getTagNo(),1));
//            }
//        }
//    }
    @RequestMapping(value = "popularTag", method = RequestMethod.GET)
    @ResponseBody
    public List<TagDTO> getPopularRecipeByTag()
    {
        List<Long> tagNoList = new ArrayList<>();
        tagNoList = recipeRepository.getPopularRecipeByTag();
        List<TagDTO> result = new ArrayList<>();
        tagNoList.forEach(t->result.add(new TagDTO(t, tagRepository.findByTagNo(t).getContent())));
        return result;
    }
    
    @RequestMapping(value="popularProfile", method = RequestMethod.GET)
    @ResponseBody
    public List<ProfileDTO> getPopularProfile()
    {
        List<String> userList = new ArrayList<>();
        userList = profileRepository.getPopularProfile();
        List<ProfileDTO> result = new ArrayList<>();
        for(int i=0; i<userList.size(); i++)
        {
            Profile getProfile = profileRepository.findByUserId(userList.get(i));
            if(getProfile!=null)
                result.add(new ProfileDTO(getProfile.getUserId(), getProfile.getNickname(), getProfile.getThumbnail()));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "searchList", method = RequestMethod.POST)
    public List<AutoCompleteDTO> searchAutocomplete(@RequestParam("keyword") String keyword){
        return searchService.searchAuto(keyword);
    }

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
        return "recipe/getTagRecipe";
    }

    @ResponseBody
    @RequestMapping(value = "followee", method = RequestMethod.GET)
    public List<ProfileDTO> getMyFollowee(@AuthenticationPrincipal User user)
    {
        if(user == null) { return null; }
        List<Profile> followList = profileService.realFollowee(user.getUserId());
        List<ProfileDTO> list = new ArrayList<>();
        followList.forEach(e->list.add(new ProfileDTO(e.getUserId(), e.getNickname())));
        return list;
    }

    @GetMapping("login")
    public String loginPage(){
//        public String loginPage(String result, Model model){

//        switch(result){
//            case "verified": model.addAttribute("verified", "true");
//                            break;
//            case "unverified": model.addAttribute("unverified", "true");
//                            break;
//        }
        return "login";
    }

    @GetMapping("special")
    public String loginPage(@RequestParam(required = false) Integer count){
        if(count!=null) {
            special.makeNewUser(count);
        } else {
            special.makeNewUser();
        }
        return "index";
    }


}