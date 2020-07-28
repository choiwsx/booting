//package org.kitchen.booting.service;
//
//import org.kitchen.booting.domain.Recipe;
//import org.kitchen.booting.repository.RecipeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class RecipeService {
//    @Autowired
//    private RecipeRepository recipeRepository;
//
//    public List<Recipe> findAll(){
//        List<Recipe> recipes = new ArrayList<>();
//        recipeRepository.findAll().forEach(e->recipes.add(e));
//        return recipes;
//    }
//
////    public List<RecipeVO> findByUno(Long uno)
////    {
//////        Optional<RecipeVO> recipe = recipeRepository.findByUno(uno);
////        List<RecipeVO> recipe = recipeRepository.findByUno(uno);
////        return recipe;
////    }
//
////    public List<RecipeVO> findByTitleLike(String keyword)
////    {
////        List<RecipeVO> recipes = new ArrayList<>();
////        recipeRepository.findByTitleLike(keyword).forEach(e->recipes.add(e));
////        return recipes;
////    }
////
////    public Optional<RecipeVO> findByRno(Long rno)
////    {
//////        Optional<RecipeVO> recipe = recipeRepository.findByUno(uno);
////        Optional<RecipeVO> recipe = recipeRepository.findByRno(rno);
////        return recipe;
////    }
////
////    public void deleteByRno(Long rno)
////    {
////        recipeRepository.deleteById(rno);
////    }
//
//    public Recipe save(Recipe recipe) {
//        recipeRepository.save(recipe);
//        return recipe;
//    }
//
//    @Transactional
//    public void deleteRecipe(Long recipeNo){
//        recipeRepository.deleteByRecipeNo(recipeNo);
//    }
//
//}
package org.kitchen.booting.service;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> findAll(){
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(e->recipes.add(e));
        return recipes;
    }

//    public List<RecipeVO> findByUno(Long uno)
//    {
////        Optional<RecipeVO> recipe = recipeRepository.findByUno(uno);
//        List<RecipeVO> recipe = recipeRepository.findByUno(uno);
//        return recipe;
//    }

//    public List<RecipeVO> findByTitleLike(String keyword)
//    {
//        List<RecipeVO> recipes = new ArrayList<>();
//        recipeRepository.findByTitleLike(keyword).forEach(e->recipes.add(e));
//        return recipes;
//    }
//
//    public Optional<RecipeVO> findByRno(Long rno)
//    {
////        Optional<RecipeVO> recipe = recipeRepository.findByUno(uno);
//        Optional<RecipeVO> recipe = recipeRepository.findByRno(rno);
//        return recipe;
//    }
//
//    public void deleteByRno(Long rno)
//    {
//        recipeRepository.deleteById(rno);
//    }
    @Transactional
    public Recipe save(Recipe recipe) {
        recipeRepository.save(recipe);
        return recipe;
    }

    public Recipe findByRecipeNo(Long recipeNo)
    {
        Optional<Recipe> recipe = recipeRepository.findById(recipeNo);
        return recipe.orElse(null);
    }

    @Transactional
    public void deleteRecipe(Long recipeNo){
        recipeRepository.deleteById(recipeNo);
    }

}