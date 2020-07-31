package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.id.IngredientId;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name="ingredient")
@Table(name="tbl_ingredient")
@IdClass(IngredientId.class)
public class Ingredient {
    @Id
    @Column(name = "ingredient_no", updatable = false, nullable = false)
    private Long ingredientNo;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_no", nullable = false, insertable = false)
    @JsonBackReference
    private Recipe recipe;

    private String name;
    private String amount;

    public Long getIngredientNo() {
        return ingredientNo;
    }

    public void setIngredientNo(Long ingredientNo) {
        this.ingredientNo = ingredientNo;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


}

