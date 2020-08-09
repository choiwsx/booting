package org.kitchen.booting.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class KitchenController {

    @Autowired
    ProfileService profileService;
    @Autowired
    LikeService likeService;
    @Autowired
    ScrapService scrapService;
//    @Autowired
//    FollowService followService;
    @Autowired
    RecipeService recipeService;

    private final Logger logger = LoggerFactory.getLogger(KitchenController.class);

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
    public String likeList(@AuthenticationPrincipal User user, Model model) {
        // 만약 로그인상태 아니면 return
        if (user == null) { return "index"; }
        // 세션에서 유저의 아이디를 받아서 리스트로 보내준다
        model.addAttribute("likes", likeService.listByUserId(user));
        // 세션이 갖고 있는 아이디 세션 갖고다니면 안해도 될듯
        model.addAttribute("user", user.getUserId());
//        logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+likeService.listByUserId(user).toString());

        return "/profile/likelist";
    }

    @GetMapping(value = "/profile/scraplist")
    public String scrapList(@AuthenticationPrincipal User user, Model model) {
        // 만약 로그인상태 아니면 return
        if (user == null) { return "/"; }
        // 세션에서 유저의 아이디를 받아서 스크랩리스트 받아서
        // 스크랩리스트의 recipeNo로 레시피 리스트 만들어서 List<Recipe>로 보내줘야 함
        model.addAttribute("scraps", scrapService.findByUserId(user));
        // 세션이 갖고 있는 아이디 세션 갖고다니면 안해도 될듯
        model.addAttribute("user", user.getUserId());

        return "/profile/scraplist";
    }

    @RequestMapping(value = "/kitchen/{userId}", method = RequestMethod.GET)
    public String get(@AuthenticationPrincipal User user,
                      @PathVariable("userId") String userId, Model model) {
        // 로그인 OO
        if (user != null) {
            // 만약 내 계정이면
            if (userId.equals(user.getUserId())) {
                model.addAttribute("profile", profileService.findByUserId(user.getUserId()));
                model.addAttribute("recipes", recipeService.findByUserId(user.getUserId()));
//                model.addAttribute("followings", followService.findByUserId(user.getUserId()));
//                model.addAttribute("followers", followService.findByFollowUserId(user.getUserId()));
                return "/kitchen/mine";
            }
            // 다른 유저의 계정 프로필볼 때
            else {
                // 계정이 비공개인지 공개인지 확인
                // true이면 비공개 false이면 공개
//                model.addAttribute("isFollow", followService.get(user.getUserId(), userId));
//                model.addAttribute("private", followService.getPrivate(userId));
                model.addAttribute("profile", profileService.findByUserId(userId));
                model.addAttribute("recipes", recipeService.findByUserId(userId));
//                model.addAttribute("followings", followService.findByUserId(userId));
//                model.addAttribute("followers", followService.findByFollowUserId(userId));
                return "/kitchen/get";
            }
        }
        // 로그인 XX
        else {
            // 계정이 비공개인지 공개인지 확인
            // true이면 비공개 false이면 공개
//            model.addAttribute("private", followService.getPrivate(userId));
            model.addAttribute("profile", profileService.findByUserId(userId));
            model.addAttribute("recipes", recipeService.findByUserId(userId));
//            model.addAttribute("followings", followService.findByUserId(userId));
//            model.addAttribute("followers", followService.findByFollowUserId(userId));
            return "/kitchen/get";
        }
    }
//    @GetMapping("/kitchen/apply")
//    public String applyList(@AuthenticationPrincipal User user, Model model) {
//        // 유저 없으면 이러케~
//        if(user == null) { return "/login"; }
//        model.addAttribute("followers", followService.followApply(user.getUserId()));
//        return "/kitchen/apply";
//    }
}
