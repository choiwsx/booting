package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.id.IngredientId;

import javax.persistence.*;

@IdClass(IngredientId.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name="ingredient")
@Table(name="tbl_ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_no", updatable = false, nullable = false)
    private Long ingredientNo;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_no", updatable = false, insertable = false)
    @JsonBackReference
    private Recipe recipe;

    private String content;

    public Long getIngredientNo() {
        return ingredientNo;
    }

    public void setIngredientNo(Long materialNo) {
        this.ingredientNo = ingredientNo;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientNo=" + ingredientNo +
                ", recipe=" + recipe +
                ", content='" + content + '\'' +
                '}';
    }
}
