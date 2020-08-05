package org.kitchen.booting.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "tag")
@Table(name = "tbl_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_no", updatable = false, nullable = false)
    private Long tagNo;

    private String content;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "tags")
    private List<Recipe> recipes;

    public boolean addRecipes(Recipe recipe){
        if(recipes==null){
            recipes = new ArrayList<>();
        }
         recipes.add(recipe);
    return recipe.addTags(this);
    }

    public Long getTagNo()
    {
        return this.tagNo;
    }

}