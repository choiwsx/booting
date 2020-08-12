package org.kitchen.booting.domain.id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {
    private String profile;
    private Long recipe;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Long getRecipe() {
        return recipe;
    }

    public void setRecipe(Long recipe) {
        this.recipe = recipe;
    }
}
