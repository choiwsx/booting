package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
//
//    public Optional<RecipeVO> findByRno(Long rno);
//    public List<RecipeVO> findByUno(Long uno);
//    public List<RecipeVO> findByTitleLike(String keyword);
    public void deleteByRecipeNo(Long recipeNo);
    public Recipe findByRecipeNo(Long recipeNo);
}