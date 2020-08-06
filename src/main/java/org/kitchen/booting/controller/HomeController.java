package org.kitchen.booting.controller;

import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.service.ProfileService;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    RecipeService recipeService;
    @Autowired
    TagService tagService;
    @Autowired
    ProfileService profileService;

    @GetMapping(value="/")
    public String indexView(@AuthenticationPrincipal User user,Model model)
    {
        model.addAttribute("tags",tagService.randomTagList());
        return "index";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public List<String> autoComplete(HttpServletRequest requset){
        List<String> list = tagService.search(requset.getParameter("term"));
        List<String> recipeList = recipeService.search(requset.getParameter("term"));
        List<String> userList = profileService.search(requset.getParameter("term"));
        for (String s : recipeList) {
            list.add(s);
        }
        for (String s: userList) {
            list.add(s);
        }
        return list;
    }

}

