package org.kitchen.booting.domain;

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
    @Id
    @Column(name="recipe_no", nullable = false)
    private Long recipeNo;

    public String getUserId() { return userId;}

    public void setUserId(String userId) { this.userId = userId; }

    public Long getRecipeNo() { return recipeNo;}

    public void setRecipeNo(Long recipeNo) { this.recipeNo = recipeNo; }

    @Override
    public String toString() {
        return "userId >>>>>>>>>>>>>>>>" + userId +
                "   recipeNo >>>>>>>>>>>>>>>>>" + recipeNo;
    }
}
