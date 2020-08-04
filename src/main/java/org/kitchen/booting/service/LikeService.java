package org.kitchen.booting.service;

import org.kitchen.booting.controller.RecipeController;
import org.kitchen.booting.domain.Like;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.repository.LikeRepository;
import org.kitchen.booting.repository.ProfileRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ProfileRepository profileRepository;

    private final Logger logger = LoggerFactory.getLogger(LikeService.class);

    public List<Recipe> listByUserId(String userId) {
        // 유저아이디로 유저가 좋아요 한 리스트에 레시피제목들 반환
        List<Recipe> recipes = new ArrayList<>();
        likeRepository.findAllByUserId(userId).forEach((e ->
                recipes.add(recipeRepository.findByRecipeNo(e.getRecipeNo()))));
        return recipes;
    }

    public List<Profile> listByRecipeNo(Long recipeNo) {
        // 레시피 넘버로 레시피를 좋아요 한 유저들의 닉네임리스트 반환
        List<Profile> profiles = new ArrayList<>();
        likeRepository.findAllByRecipeNo(recipeNo).forEach((e ->
                profiles.add(profileRepository.findByUserId(e.getUserId()))));
        return profiles;
    }

    public Like getLike(String userId, Long recipeNo) {
        Like like = likeRepository.findByUserIdAndRecipeNo(userId, recipeNo);
        return like;
    }

    public Like save(Like like) {
        likeRepository.save(like);
        return like;
    }

    public List<Like> findAll() {
        return likeRepository.findAll();
    }

    public void delete(Like like) {
        likeRepository.delete(like);
    }
}
