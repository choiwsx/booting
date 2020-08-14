package org.kitchen.booting.controller;

import org.kitchen.booting.domain.*;
import org.kitchen.booting.domain.id.FollowId;
import org.kitchen.booting.domain.id.LikeId;
import org.kitchen.booting.domain.id.ScrapId;
import org.kitchen.booting.repository.ProfileRepository;
import org.kitchen.booting.service.*;
import org.kitchen.booting.domain.userauth.EmailVerificationToken;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.event.NewUserEvent;
import org.kitchen.booting.event.OnGenerateResetLinkEvent;
import org.kitchen.booting.event.OnRegenerateEmailVerificationEvent;
import org.kitchen.booting.exception.InvalidTokenRequestException;
import org.kitchen.booting.exception.UserRegistrationException;
import org.kitchen.booting.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
public class JsonController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final RecipeService recipeService;
    private final ScrapService scrapService;
    private final TagService tagService;
    private final LikeService likeService;
    //    private final FollowService followService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    @Autowired
    public JsonController(UserService userService, RecipeService recipeService, LikeService likeService,
                          ScrapService scrapService, TagService tagService, ApplicationEventPublisher applicationEventPublisher,
                          CategoryRepository categoryRepository, ProfileService profileService)
    {
        this.userService = userService;
        this.recipeService = recipeService;
        this.scrapService = scrapService;
        this.tagService = tagService;
        this.likeService = likeService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.categoryRepository = categoryRepository;
        this.profileService = profileService;
    }

    @PostMapping("/recipe/ajaxTest")
    public void createRecipe(@RequestBody Recipe recipe) {
        logger.info("@@@" + recipe);
        tagService.insert(recipe);
        recipeService.save(recipe);
    }
    @PostMapping("/profile/edit")
    public void editProfile(@RequestBody Profile profile)
    {
        Profile editProfile = profileService.findByUserId(profile.getUserId());
        editProfile.setThumbnail(profile.getThumbnail());
        editProfile.setBio(profile.getBio());
        editProfile.setNickname(profile.getNickname());
        profileService.save(editProfile);
    }

    @PostMapping("/category/create")
    public void createMainCategory(@RequestBody Category category)
    {
        categoryRepository.save(category);
    }

    @PostMapping("/user/edit")
    public void editUser(@RequestBody User user) {
        User updateUser = userService.updateUser(user);
        userService.save(updateUser);
    }

    @PostMapping("/recipe/updateTest")
    public void updateRecipe(@RequestBody Recipe recipe) {
//        logger.info("@@@@@"+recipe.getTitle());
//        recipe.setTitle(recipe.getTitle());
//        logger.info("@@@@@@"+recipe.getSteps()+"");
//        recipe.setSteps();
        logger.info(recipe + "");

//        if(recipe!=null) {
//            logger.info("@"+recipe.getSteps());
        recipeService.save(recipe);
//        }
    }

    @PostMapping("/recipe/saveScrapAjax")
    public void createScrap(@RequestBody ScrapId scrapId) {
        logger.info("스크랩하기 >>>>>>>>>>>>>>>>> 되나요?");
        String userId = scrapId.getUser();
        Long recipeNo = scrapId.getRecipe();
        User user = userService.findByUserId(userId);
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);

        // userId로 scrapList 찾아서 이미 있는 recipeNo이면 return
        if (scrapService.getScrap(userId, recipeNo) != null) {
            logger.info("이미 있어서 저장안됨~ 하하하!");
            return;
        }
        // session에 userId가 없거나 recipeNo가 없으면 return
        if (userId == null || recipeNo == null) {
            return;
        }
        Scrap scrap = new Scrap(user, recipe);
        scrapService.save(scrap);
    }

    @PostMapping("/recipe/deleteScrapAjax")
    public void deleteScrap(@RequestBody ScrapId scrapId) {
        logger.info("스크랩취소 >>>>>>>>>>>>>>>>> 되나요?");
        String userId = scrapId.getUser();
        Long recipeNo = scrapId.getRecipe();

        // 찾아봤는데 어차피 없으면 삭제안됨
        if (scrapService.getScrap(userId, recipeNo) == null) {
            logger.info("애초에 없어서 취소안됨~ 하하하!");
            return;
        }
        // session에 userId가 없거나 recipeNo가 없으면 return
        if (userId == null || recipeNo == null) {
            return;
        }
        Scrap scrap = scrapService.get(userId, recipeNo);
        scrapService.delete(scrap);
    }

    @GetMapping(value = "recipe/goScrap/{userId}/{recipeNo}")
    public ResponseEntity<?> goScrap(@PathVariable String userId, @PathVariable Long recipeNo) {
        Scrap scrap = scrapService.get(userId, recipeNo);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/recipe/saveLikeAjax")
    public void saveLike(@RequestBody LikeId likeId) {
        logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 좋아요성공?!");
        String userId = likeId.getUser();
        Long recipeNo = likeId.getRecipe();
        User user = userService.findByUserId(userId);
        Recipe recipe = recipeService.findByRecipeNo(recipeNo);

        // 이미 좋아요 테이블에 있으면 안됨
        if(likeService.getLike(userId, recipeNo) != null) {
            logger.info("이미 좋아요 있어서 저장 안됨 하하하!");
            return;
        }
        // session에 userId가 없거나 recipeNo가 없으면 return
        if(userId == null || recipeNo == null) { return; }

        likeService.save(user, recipe);
    }

    @PostMapping("/recipe/deleteLikeAjax")
    public void deleteLike(@RequestBody LikeId likeId) {
        logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 좋아요취소?!");
        String userId = likeId.getUser();
        Long recipeNo = likeId.getRecipe();

        // 애초에 테이블에 없으면 삭제 안됨
        if(likeService.getLike(userId, recipeNo) == null) {
            logger.info("애초에 좋아요 없어서 취소 안됨 하하하!");
            return;
        }
        // session에 userId가 없거나 recipeNo가 없으면 return
        if(userId == null || recipeNo == null) { return; }
        Like like = likeService.get(userId, recipeNo);
        likeService.delete(like);
    }

    @GetMapping(value = "recipe/goLike/{userId}/{recipeNo}")
    public ResponseEntity<?> goLike(@PathVariable String userId, @PathVariable Long recipeNo) {
        Like like = likeService.get(userId, recipeNo);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/kitchen/saveFollowAjax")
    public void saveFollow(@RequestBody FollowId followId) {
        // 애초에 내가 팔로우한 유저이면 팔로우 안됨
        // userId없거나 followUserId 없으면 return;
//        User user = userService.findByUserId(followId.getUser());
//        User followUser = userService.findByUserId(followId.getFollowUser());
        userService.saveFollow(followId.getUser(),followId.getFollowUser());
    }

    @PostMapping("/kitchen/deleteFollowAjax")
    public void deleteFollow(@RequestBody FollowId followId) {
        userService.deleteFollow(followId.getUser(),followId.getFollowUser());
    }
//
//    @PostMapping("/kitchen/updateFollowAjax")
//    public void updateFollow(@RequestBody FollowId followId) {
//        // 비공개 사용자가 수락 누르면 status 0(false)으로 바꿔줌
//        // regDate왜 안넘어오쥐,,,
//        Follow follow = followService.get(followId.getUser(), followId.getFollowUser());
//        follow.setStatus(false);
//
//        followService.save(follow);
//    }

    @GetMapping(value = "/kitchen/goFollow/{userId}/{followUserId}")
    public ResponseEntity<?> goFollow(@PathVariable String userId, @PathVariable String followUserId)
    {
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @GetMapping("/api/auth/registrationConfirmation")
    public ResponseEntity confirmRegistration(@RequestParam("token") String token) {

        return userService.confirmEmailRegistration(token)
                .map(user -> new ResponseEntity(HttpStatus.OK))
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", token, "Failed to confirm. Please generate a new email verification request"));
    }

    /**
     * Entry point for the user registration process. On successful registration,
     * publish an event to generate email verification token
     */

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return userService.registerNewUser(userRegistrationDTO)
                .map(user -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/api/auth/registrationConfirmation");
                    NewUserEvent newUserEvent = new NewUserEvent(user, urlBuilder);
                    applicationEventPublisher.publishEvent(newUserEvent);
                    logger.info("Registered User returned [API[: " + user);
                    return ResponseEntity.ok(user);
                })
                .orElseThrow(() -> new UserRegistrationException(userRegistrationDTO.getUserId(), "Missing user object in database"));
    }






    @GetMapping("/api/auth/resendRegistrationToken")
    public ResponseEntity resendRegistrationToken(@RequestParam("token") String existingToken) {

        EmailVerificationToken newEmailToken = userService.recreateRegistrationToken(existingToken)
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken, "User is already registered. No need to re-generate token"));

        return Optional.ofNullable(newEmailToken.getUser())
                .map(registeredUser -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/api/auth/registrationConfirmation");
                    OnRegenerateEmailVerificationEvent regenerateEmailVerificationEvent = new OnRegenerateEmailVerificationEvent(registeredUser, urlBuilder, newEmailToken);
                    applicationEventPublisher.publishEvent(regenerateEmailVerificationEvent);
                    logger.info("@@@email다시@@@" + regenerateEmailVerificationEvent.toString());
                    logger.info("@@@유저@@@" + registeredUser.toString());
                    logger.info("@@@토큰@@@" + newEmailToken);

                    return ResponseEntity.ok(HttpStatus.OK);
                })
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken, "No user associated with this request. Re-verification denied"));
    }

    @PostMapping("/admin/category/delete/{categoryNo}")
    public void delCategory(@PathVariable("categoryNo") Long categoryNo)
    {

        Optional<Category> getCategory = categoryRepository.findById(categoryNo);
        logger.info("!!!!!!!"+getCategory.get().getSubCategories().toString());
        if(getCategory.isPresent()) {
            getCategory.get().getSubCategories().forEach(sub->sub.setMainCategory(null));
            getCategory.get().getRecipes().forEach(recipe->recipe.setCategory(null));
            getCategory.get().getSubCategories().clear();
//            logger.info("@@@@@@"+getCategory.get().getSubCategories().toString());
            categoryRepository.delete(getCategory.get());
        }
    }


//    @GetMapping("/validate/")
//    public String validateEmail() {
//
//    }

}
