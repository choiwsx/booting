package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Category;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.springframework.data.domain.Page;
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
    public List<Recipe> findByCategory(Category category);
    public Page<Recipe> findByCategory(Category category, Pageable pageable);
    public Page<Recipe> findByTitleContaining(String keyword, Pageable pageable);
    public List<Recipe> findByTitleContaining(String keyword);
    public Page<Recipe> findByContentContaining(String keyword, Pageable pageable);
    @Query(value="SELECT title FROM tbl_recipe where title like %:keyword%", nativeQuery = true)
    public List<String> search(@Param("keyword") String keyword);
    public List<Recipe> findAllByOrderByUpDate();

    @Query(value="SELECT tag_no,COUNT(*) as c FROM tbl_recipe_tag GROUP BY tag_no ORDER BY c DESC LIMIT 20", nativeQuery = true)
    public List<Long> getPopularRecipeByTag();

//    @Query(value="select recipe_no, category_no, title, thumbnail FROM tbl_recipe where title LIKE CONCAT('%',:keyword,'%') ORDER BY RAND() LIMIT 4;", nativeQuery = true)
//    public List<Recipe> acTitle(@Param("keyword") String keyword);

    @Query(value="select thumbnail FROM tbl_recipe where title like %:keyword% ORDER BY RAND() LIMIT 4;", nativeQuery = true)
    public List<Recipe> acThumbnail(@Param("keyword") String keyword);

}