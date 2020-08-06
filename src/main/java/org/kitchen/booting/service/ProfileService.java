package org.kitchen.booting.service;

import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> findAll(){
//        List<Profile> profiles = new ArrayList<>();
//        profileRepository.findAll().forEach(e-> profiles.add(e));
        return profileRepository.findAll();
    }

    public Optional<Profile> findById(String userId)
    {
//        Optional<RecipeVO> recipe = recipeRepository.findByUno(uno);
        Optional<Profile> user = profileRepository.findById(userId);
        return user;
    }
//
//    public List<UserVO> findByTitleLike(String keyword)
//    {
//        List<UserVO> users = new ArrayList<>();
//        userRepository.findByTitleLike(keyword).forEach(e->recipes.add(e));
//        return recipes;
//    }

//    public Optional<RecipeVO> findByRno(Long rno)
//    {
////        Optional<RecipeVO> recipe = recipeRepository.findByUno(uno);
//        Optional<RecipeVO> recipe = recipeRepository.findByRno(rno);
//        return recipe;
//    }

    //public void deleteByRno(Long rno)
//    {
//        recipeRepository.deleteById(rno);
//    }

    public Profile save(Profile profile)
    {
        profileRepository.save(profile);
        return profile;
    }

    public void delete(String userId) {
        profileRepository.deleteById(userId);
    }

    public List<String> search(String keyword){
        return profileRepository.search(keyword);
    }


}