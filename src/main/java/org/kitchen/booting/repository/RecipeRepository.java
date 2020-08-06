package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, PagingAndSortingRepository<Recipe, Long> {
    //
//    public Optional<RecipeVO> findByRno(Long rno);
    public List<Recipe> findByProfile(Profile profile);
//    public List<RecipeVO> findByTitleLike(String keyword);
    public void deleteByRecipeNo(Long recipeNo);
    public Recipe findByRecipeNo(Long recipeNo);
    public Page<Recipe> findByTitleContaining(String keyword, Pageable pageable);
    public List<Recipe> findByTitleContaining(String keyword);
    public Page<Recipe> findByContentContaining(String keyword, Pageable pageable);
    @Query(value="SELECT title FROM tbl_recipe where title like %:keyword%", nativeQuery = true)
    public List<String> search(@Param("keyword") String keyword);
}