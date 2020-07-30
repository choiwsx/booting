package org.kitchen.booting.service;

import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.repository.ProfileRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Transactional
    public List<Recipe> searchRecipe(String keyword)
    {
        List<Recipe> recipes = recipeRepository.findByTitleContaining(keyword);
        return recipes;
    }

    @Transactional
    public List<Profile> searchProfile(String keyword)
    {
        List<Profile> profiles = profileRepository.findByUserIdContaining(keyword);
        return profiles;
    }

}
