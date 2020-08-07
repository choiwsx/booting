package org.kitchen.booting.domain;

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
    @Id
    @Column(name="recipe_no", nullable = false)
    private Long recipeNo;

    @CreationTimestamp
    private Date regDate;

    public String getUserId() { return userId;}

    public void setUserId(String userId) { this.userId = userId; }

    public Long getRecipeNo() { return recipeNo;}

    public void setRecipeNo(Long recipeNo) { this.recipeNo = recipeNo; }

    public Date getRegDate() { return regDate; }

    public void setRegDate(Date regDate) {this.regDate = regDate; }

    @Override
    public String toString() {
        return " UserId >>>>>>>>>>>>" + userId +
                " RecipeNo >>>>>>>>>>>" + recipeNo +
                " RegDate >>>>>>>>>>>>" + regDate;
    }
}
