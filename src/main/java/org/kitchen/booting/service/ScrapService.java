package org.kitchen.booting.service;

import org.kitchen.booting.controller.RecipeController;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Scrap;
import org.kitchen.booting.domain.id.ScrapId;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.ProfileRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.kitchen.booting.repository.ScrapRepository;
import org.kitchen.booting.repository.userauth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScrapService {
    @Autowired
    private ScrapRepository scrapRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    public List<Scrap> findByProfile(Profile profile) {
        List<Scrap> scraps = scrapRepository.findAllByProfile(profile);
        logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%7777777777777"+scraps.toString());

        return scraps;
    }

    public Scrap getScrap(String userId, Long recipeNo) {
        Optional<Scrap> scrap = scrapRepository.findById(new ScrapId(userId, recipeNo));

//                findByUserAndRecipe(userId, recipeNo);
        return scrap.orElse(null);
    }

    public Scrap get(String userId, Long recipeNo) {
        Profile profile = profileRepository.findByUserId(userId);
        Recipe recipe = recipeRepository.findByRecipeNo(recipeNo);
        return scrapRepository.findByProfileAndRecipe(profile, recipe);
    }

    public void save(Scrap scrap) {
//        Optional<Scrap> scrap = scrapRepository.findById(new ScrapId(userId, recipeNo));
//        if(scrap.isPresent()) scrapRepository.save(scrap.get());
        scrapRepository.save(scrap);
    }

    public void delete(Scrap scrap) {
//        Optional<Scrap> scrap = scrapRepository.findById(new ScrapId(userId, recipeNo));
//        if(scrap.isPresent()) scrapRepository.delete(scrap.get());
        scrapRepository.delete(scrap);
    }
}