package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.id.ScrapId;

import javax.persistence.*;

@IdClass(ScrapId.class)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "scrap")
@Table(name = "tbl_scrap")
public class Scrap {

    @Id
    @Column(name="user_id", nullable = false)
    private String userId;
//    @Id
//    @Column(name="recipe_no", nullable = false)
//    private Long recipeNo;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_no", updatable = false, insertable = false)
    @JsonBackReference
    private Recipe recipe;

    public String getUserId() { return userId;}

    public void setUserId(String userId) { this.userId = userId; }

//    public Long getRecipeNo() { return recipeNo;}
//
//    public void setRecipeNo(Long recipeNo) { this.recipeNo = recipeNo; }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "userId >>>>>>>>>>>>>>>>" + userId +
                "   recipe >>>>>>>>>>>>>>>>>" + recipe.toString();
    }
}
