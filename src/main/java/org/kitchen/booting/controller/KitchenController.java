package org.kitchen.booting.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.service.LikeService;
import org.kitchen.booting.service.ProfileService;
import org.kitchen.booting.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class KitchenController {

    @Autowired
    ProfileService profileService;
    @Autowired
    LikeService likeService;
    @Autowired
    ScrapService scrapService;

    @GetMapping(value = "/profile/register")
    public void profileForm(@ModelAttribute Profile profile) {
        //return "profile/register";
    }

    @PostMapping(value = "/profile/register")
    public void saveProfile(@ModelAttribute Profile profile) {
        profileService.save(profile);
       // return "profile/register";
    }

    @GetMapping(value = "/profile/list")
    public void profileList(Model model) {
        model.addAttribute("profiles", profileService.findAll());
    }

    @GetMapping(value = "/profile/likelist")
    public String likeList(Model model) {
        // 만약 로그인상태 아니면 return
        // 세션에서 유저의 아이디를 받아서 리스트로 보내준다
        model.addAttribute("likes", likeService.listByUserId("user01"));
        // 세션이 갖고 있는 아이디 세션 갖고다니면 안해도 될듯
        model.addAttribute("user", "user01");

        return "/profile/likelist";
    }
    
    @GetMapping(value = "/profile/scraplist")
    public String scrapList(Model model) {
        // 만약 로그인상태 아니면 return
        // 세션에서 유저의 아이디를 받아서 스크랩리스트 받아서
        // 스크랩리스트의 recipeNo로 레시피 리스트 만들어서 List<Recipe>로 보내줘야 함
        model.addAttribute("scraps", scrapService.findByUserId("user01"));
        // 세션이 갖고 있는 아이디 세션 갖고다니면 안해도 될듯
        model.addAttribute("user", "user01");

        return "/profile/scraplist";
    }

}
