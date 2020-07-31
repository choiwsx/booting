package org.kitchen.booting.service;

import org.kitchen.booting.controller.RecipeController;
import org.kitchen.booting.controller.SearchController;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Tag;
import org.kitchen.booting.repository.ProfileRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.kitchen.booting.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private TagRepository tagRepository;

    private final Logger logger = LoggerFactory.getLogger(SearchController.class);

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

    @Transactional
    public List<Recipe> searchTag(String keyword)
    {
        List<Tag> tags = tagRepository.findByContentContaining(keyword);
        List<Long> tagList = new ArrayList<>();
        tags.forEach(e->tagList.add(e.getTagNo()));
        Set<Long> recipeList = new HashSet<>();
        List<Long> recipeListtmp = new ArrayList<>();
        for(int i=0; i<tagList.size(); i++)
        {
            recipeListtmp = tagRepository.findRecipeNoByTagNo(tagList.get(i));
            recipeListtmp.forEach(a->recipeList.add(a));
        }

        List<Recipe> tagRecipeList = new ArrayList<>();
        recipeList.forEach(a->tagRecipeList.add(recipeRepository.findByRecipeNo(a)));
        return tagRecipeList;
    }

}
