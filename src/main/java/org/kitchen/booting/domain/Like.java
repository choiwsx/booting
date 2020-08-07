package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.kitchen.booting.domain.id.ScrapId;

import javax.persistence.*;
import java.util.Date;

@IdClass(ScrapId.class)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "like")
@Table(name = "tbl_like")
public class Like {
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

    @CreationTimestamp
    private Date regDate;

    public String getUserId() { return userId;}

    public void setUserId(String userId) { this.userId = userId; }

//    public Long getRecipeNo() { return this.recipe.getRecipeNo();}
//
//    public void setRecipeNo(Long recipeNo) { this.recipeNo = recipeNo; }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Date getRegDate() { return regDate; }

    public void setRegDate(Date regDate) {this.regDate = regDate; }

    @Override
    public String toString() {
        return " UserId >>>>>>>>>>>>" + userId +
                " Recipe >>>>>>>>>>>" + recipe.toString() +
                " RegDate >>>>>>>>>>>>" + regDate;
    }
}
